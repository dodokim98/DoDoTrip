<script setup>
import { ref, onMounted } from "vue";
import { listGugun, listSido, search } from "@/api/attraction";
import { useMemberStore } from "@/stores/member";
import { createAttractionLike, deleteAttractionLike2 } from "@/api/like";
import 'vue3-carousel/dist/carousel.css'
import { Carousel, Slide } from 'vue3-carousel';
import noImage from "@/assets/noimage.gif";

const areas = ref([]);
const sigungus = ref([]);
const searchArea = ref("0");
const searchSigungu = ref("0");
const searchContentId = ref("0");
const searchKeyword = ref("");
const tripList = ref([]);
const positions = ref([]);
const map = ref(null);

const userEmail = ref();
const memberStore = useMemberStore();
const { getUserInfo } = memberStore;

// 지도를 초기화하는 함수
const initMap = () => {
  // 여기에 실제 지도 초기화 코드를 넣어주세요
  const mapOptions = {
    center: new kakao.maps.LatLng(37.5665, 126.978),
    level: 5,
  };
  map.value = new kakao.maps.Map(document.getElementById("map"), mapOptions);
};
onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); 
  const userInfo =  await getUserInfo(token);
  console.log("userinfo : " , userInfo);
  userEmail.value = userInfo.userEmail;
  console.log("userEmail : ", userEmail.value);
  // 여기서 kakao 맵을 화면에 반영합니다.
  // 카카오 라이브러리 정보 및 map을 확인
  if (window.kakao && window.kakao.maps) {
    initMap(); // 지도 초기화 - 상단에 function 선언해 있습니다.
    initialize();
  } else {
    // 카카오map 라이브러리 정보 셋팅
    // script태그를 생성해서 apikey를 셋팅해야합니다.
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => {
      kakao.maps.load(initMap);
      initialize();
    };
    // 아래 appkey 부분에 본인의 apikey를 셋팅해주세요.
    // (카카오 developers에 현재 vue 도메인 정보가 등록되어 있어야합니다.)
    script.src =
      "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8d90f6bcefe570a9fb23f137ad334767";
    document.head.appendChild(script);
  }
});

function initialize() {
  loadAreas(); // 검색할 지역 목록 초기화
  loadSigunguOptions(); // 검색할 군구 목록 초기화
}

function loadAreas() {
  listSido(
    (response) => {
      // 받은 데이터를 처리
      areas.value = response.data;
      console.log(areas.value);
      loadSigunguOptions();
    },
    (error) => {
      console.error(error);
    }
  );
}

function loadSigunguOptions() {
  const sidoCode = searchArea.value;
  console.log(sidoCode);
  // listGugun 함수는 필요에 따라 구현
  listGugun(
    { sidoCode: sidoCode },
    (response) => {
      sigungus.value = response.data;
      console.log(response.data);
    },
    (error) => {
      console.error(error);
    }
  );
}

function searchAttraction() {
  const params = {
    sidoCode: searchArea.value,
    gugunCode: searchSigungu.value,
    contentTypeId: searchContentId.value,
    keyword: searchKeyword.value,
  };

  search(
    params,
    (response) => {
      makeList(response.data);
    },
    (error) => {
      console.error(error);
    }
  );
}

function makeList(data) {
  console.log(data);
  positions.value = [];
  const trips = data.attractions;
  console.log("trips : ",trips);

  tripList.value = trips.map((area) => {
    const markerInfo = {
      title: area.title,
      latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
    };
    positions.value.push(markerInfo); // positions 배열에 마커 정보 추가
    return {
      contentId: area.contentId,
      firstImage: area.firstImage,
      title: area.title,
      addr: `${area.addr1} ${area.addr2}`,
      latitude: area.latitude,
      longitude: area.longitude,
      markerInfo: markerInfo,
      favorite: false,
    };
  });
  console.log("tripList: ", tripList.value);
  console.log("positions : ", positions.value);
  displayMarker(positions.value);
}

function displayMarker() {
  const imageSrc =
    "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

  for (let i = 0; i < positions.value.length; i++) {
    const imageSize = new kakao.maps.Size(24, 35);
    const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    const marker = new kakao.maps.Marker({
      map: map.value,
      position: positions.value[i].latlng,
      title: positions.value[i].title,
      image: markerImage,
    });

    (function (marker, title) {
      kakao.maps.event.addListener(marker, "click", function () {
        const infowindow = new kakao.maps.InfoWindow({
          content: '<div style="padding:5px;">' + title + "<br></div>",
        });
        infowindow.open(map.value, marker);
      });
    })(marker, positions.value[i].title);
  }

  map.value.setCenter(positions.value[0].latlng);
}

function moveCenter(lat, lng) {
  map.value.setCenter(new kakao.maps.LatLng(lat, lng));
}


function handlePlaceClick(place) {
  toggleFavorite (place);
}

// ...

function toggleFavorite(place) {
  place.favorite = !place.favorite; // 찜 여부 토글
  console.log("place.favorite : ",place.favorite);
  // 서버에 찜 여부 업데이트 요청
  const attractionLikeDto = {
    attractionInfoContentId: place.contentId,
    memberUserEmail: userEmail.value,
  };
  if(place.favorite){
    createAttractionLike(
      attractionLikeDto,
      (response) => {
        let msg = "찜 목록 등록 처리 시 문제가 발생했습니다.";
        if (response.status == 200) msg = "찜 목록 등록이 완료되었습니다.";
        alert(msg);
      },
      (error) => {
        console.log(error);
        // 실패한 경우 다시 원래대로 복원
        place.favorite = !place.favorite;
      }
    );
  }else{
    deleteAttractionLike2(
      place.contentId,
      (response) => {
        let msg = "찜 목록 삭제 처리 시 문제가 발생했습니다.";
        if (response.status == 200) msg = "찜 목록 삭제가 완료되었습니다.";
        alert(msg);
      },
      (error) => {
        console.log(error);
        // 실패한 경우 다시 원래대로 복원
        place.favorite = !place.favorite;
      }
    );
  }
}
const getImageSource = (place) => {
  console.log("place.firstImage : ", place.firstImage);
  try {
    return place.firstImage || noImage;
  } catch (error) {
    return noImage;
  }
};

</script>

<template>
  <div>
    <!-- 검색 폼 -->
    <form class="d-flex my-3" @submit.prevent="searchAttraction">
      <select
        class="form-select me-2"
        v-model="searchArea"
        @change="loadSigunguOptions"
        style="width: 200px"
      >
        <option value="0">지역 선택</option>
        <option
          v-for="area in areas"
          :key="area.sidoCode"
          :value="area.sidoCode"
        >
          {{ area.sidoName }}
        </option>
      </select>
      <select
        class="form-select me-2"
        v-model="searchSigungu"
        style="width: 350px"
      >
        <option value="0">군구 선택</option>
        <option
          v-for="sigungu in sigungus"
          :key="sigungu.gugunCode"
          :value="sigungu.gugunCode"
        >
          {{ sigungu.gugunName }}
        </option>
      </select>
      <select
        v-model="searchContentId"
        class="form-select me-2"
        id="search-content-id"
        style="width: 800px"
      >
        <option selected value="0">관광지 유형</option>
        <option value="12">관광지</option>
        <option value="14">문화시설</option>
        <option value="15">축제공연행사</option>
        <option value="25">여행코스</option>
        <option value="28">레포츠</option>
        <option value="32">숙박</option>
        <option value="38">쇼핑</option>
        <option value="39">음식점</option>
      </select>
      <input
        v-model="searchKeyword"
        class="form-control me-2"
        placeholder="검색어"
        type="search"
        style="flex-grow: 2"
      />
      <button class="btn btn-outline-dark" type="submit">검색</button>
    </form>

    <!-- 지도 표시 -->
    <div id="map" style="width: 100%; height: 400px"></div>

    <!-- 관광지 목록 표시 -->
    <div class="col" id="mapList" v-if="tripList.length > 0">
      <!-- Carousel for tripList -->
      <carousel :items-to-show="4">
        <slide v-for="place in tripList" :key="place.title">
          <!-- Your content for each slide -->
          <div class="place-item" @click="moveCenter(place.latitude, place.longitude)">
            <img id="mapImg"
              :src=getImageSource(place)
              alt="대표이미지"
              style="width: 277px; height: 180px"
            />
            <p class="place-title">{{ place.title }}</p>
            <p class="place-addr">{{ place.addr }}</p>
            <button
              v-if="userEmail"
              class="favorite-btn"
              @click="handlePlaceClick(place)"
              :class="{ 'active': place.favorite }"
            >
              <i class="fas fa-heart" :style="{ color: place.favorite ? 'red' : 'white' }"></i>
            </button>
          </div>
        </slide>
      </carousel>
    </div>

  </div>
</template>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css');

#mapList {
  height: 350px;
  width: 1300px;
  border: 1px solid black;
  background-color: #EEF1F5;
}

#mapImg {
  border-radius: 10%;
}

.favorite-btn{
  border: 0px;
  background-color: #EEF1F5;
}

#map {
  width: 400px;
  height: 400px;
  border: 3px solid #010101ac;
  margin-bottom: 5%;
}

.place-item {
  border: 1px solid #ddd; /* 테두리 추가 */
  border-radius: 10px; /* 둥근 테두리 추가 */
  margin: 10px; /* 간격 추가 */
  padding: 10px; /* 안쪽 여백 추가 */
}

.place-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.place-addr {
  font-size: 14px;
  color: #555;
  margin-bottom: 10px;
}
</style>
