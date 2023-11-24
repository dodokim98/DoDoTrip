<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { listGugun, listSido, search } from "@/api/attraction";
import AttractionListItem from "./item/AttractionListItem.vue";

const serviceKey =
  "A68stQHw7AmaDmOpglroxDFBYJl7mrqc8rWDlHJg7k%2BHIF%2FcRYuykizHijQ6AgbrTZS7pLH5gN05fY1v2z0idw%3D%3D";
const areas = ref([]);
const sigungus = ref([]);
const searchArea = ref("0");
const searchSigungu = ref("0");
const searchContentId = ref("0");
const searchKeyword = ref("");
const tripList = ref([]);
const positions = ref([]);
const map = ref(null);

// 지도를 초기화하는 함수
const initMap = () => {
  // 여기에 실제 지도 초기화 코드를 넣어주세요
  const mapOptions = {
    center: new kakao.maps.LatLng(37.5665, 126.978),
    level: 5,
  };
  map.value = new kakao.maps.Map(document.getElementById("map"), mapOptions);
};
onMounted(() => {
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
      "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=fb99002f65cf176055792c721da0e489";
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
  document.querySelector("table").setAttribute("style", "display: ;");
  console.log(data);
  positions.value = [];
  const trips = data.attractions;
  console.log(trips);

  tripList.value = trips.map((area) => {
    const markerInfo = {
      title: area.title,
      latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
    };
    positions.value.push(markerInfo); // positions 배열에 마커 정보 추가
    return {
      contentId : area.contentId,
      firstImage: area.firstImage,
      title: area.title,
      addr: `${area.addr1} ${area.addr2}`,
      latitude: area.latitude,
      longitude: area.longitude,
      markerInfo: markerInfo,
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
    <div id="map"></div>

    <!-- 관광지 목록 표시 -->
    <div class="row">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>대표이미지</th>
            <th>관광지명</th>
            <th>주소</th>
            <th>위도</th>
            <th>경도</th>
          </tr>
        </thead>
        <tbody>
          <AttractionListItem
            v-for="place in tripList"
            :key="place.title"
            :place="place"
            @click="moveCenter(place.latitude, place.longitude)"
          ></AttractionListItem>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 400px;
}
</style>
