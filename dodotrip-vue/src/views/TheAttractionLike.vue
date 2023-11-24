<script setup>
// import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRouter } from "vue-router";
import { getAttractionLikeList, deleteAttractionLike, getAttractionDescription} from "@/api/like";
import { useMemberStore } from "@/stores/member";
import { useAttractionDetailStore} from '@/stores/attractionDetail.js'
import { storeToRefs } from 'pinia';

const attractionDetailStore = useAttractionDetailStore();
const { updatedTripListItem } = storeToRefs(attractionDetailStore);
// import router from '@/router';//////

const router = useRouter();

const tripList = ref([]);
const positions = ref([]);
const map = ref(null); 

const likedAttractions = ref([]);

const userEmail = ref();


const memberStore = useMemberStore();

const { getUserInfo } = memberStore;


onMounted(async () => {
    let token = sessionStorage.getItem("accessToken"); 
    const userInfo =  await getUserInfo(token);
    console.log("userinfo : " , userInfo);
    userEmail.value = userInfo.userEmail;
    console.log("userEmail : ", userEmail.value);
    // 컴포넌트가 마운트되었을 때 찜한 목록을 가져오기
    getLikedAttractions();
    // 카카오맵 스크립트 로드 코드
    const script = document.createElement("script");
    script.onload = () => {
      kakao.maps.load(() => {
        getLikedAttractions();
      });
    };
    script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8d90f6bcefe570a9fb23f137ad334767";
    document.head.appendChild(script);
});

// // 지도를 초기화하는 함수
const initMap = () => {
  // 여기에 실제 지도 초기화 코드를 넣어주세요
  const mapOptions = {
    center: new kakao.maps.LatLng(37.5665, 126.978),
    level: 7,
  };
  map.value = new kakao.maps.Map(document.getElementById("map"), mapOptions);
};

function getLikedAttractions() {
  // API 함수를 호출하여 찜한 목록을 가져오기
    getAttractionLikeList(userEmail.value, ({data}) => {
        console.log("list: ",data)
        likedAttractions.value = data;
        console.log("likedAttractions : ",likedAttractions.value)
        // 여기서 kakao 맵을 화면에 반영합니다.
        // 카카오 라이브러리 정보 및 map을 확인
        if (window.kakao && window.kakao.maps) {
          initMap(); // 지도 초기화 - 상단에 function 선언해 있습니다.
        } else {
          // 카카오map 라이브러리 정보 셋팅
          // script태그를 생성해서 apikey를 셋팅해야합니다.
          const script = document.createElement("script");
          /* global kakao */
          script.onload = () => {
            kakao.maps.load(initMap);
          };
          // 아래 appkey 부분에 본인의 apikey를 셋팅해주세요.
          // (카카오 developers에 현재 vue 도메인 정보가 등록되어 있어야합니다.)
          script.src =
            "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8d90f6bcefe570a9fb23f137ad334767";
          document.head.appendChild(script);
        }
        makeList(likedAttractions.value);
    },
    (error) => {
      // 에러 발생 시 처리
      console.error('찜한 목록을 불러오는 중 에러 발생', error);
    }
  );
}

function deleteAttractionLikeDetail(likeNo){
  console.log("like : ", likeNo);
    deleteAttractionLike(
      likeNo,
      (response) => {
        let msg = "찜 목록 삭제 처리 시 문제가 발생했습니다.";
        if(response.status == 200) msg = "찜 목록 삭제가 완료되었습니다.";
        alert(msg);
        getLikedAttractions();
      },
      (error) => console.log(error)
    )
}

function makeList(data) {
  console.log("data: ", data);
  positions.value = [];
  const trips = data;
  console.log(trips);

  tripList.value = trips.map((area) => {
    console.log("area : ", area);
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
      overview: '',
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

const moveDetail = (contentId) => {
  // getAttractionDescription 함수를 이용하여 overview를 가져오기
  console.log(contentId);
  getAttractionDescription(contentId, 
    (response) => {
      console.log("data data : ", response.data);
      // 가져온 overview를 이용하여 tripList에 추가
      const updatedTripList = tripList.value.map((trip) => {
        if (trip.contentId === contentId) {
          // contentId가 일치하면 overview만 업데이트한 객체를 리턴
          return {
            ...trip,
            overview: response.data.overview,
          };
        }
        // contentId가 일치하지 않으면 기존의 객체 그대로 리턴
        return trip;
      });
      console.log("updateTripList", updatedTripList);
      updatedTripListItem.value = updatedTripList;
      console.log("updatedTripListItem", updatedTripListItem.value);
      // 페이지 이동 및 전체 updatedTripList를 params로 전달
      router.push({ name: "attraction-detail" });
    },
    (error) => {
      // 에러 발생 시 처리
      console.error('overview를 불러오는 중 에러 발생', error);
    }
  );
};

</script>

<template>
  <div class="container text-center mt-3">
    <router-view></router-view>
  </div>
  <div class="main-container">
    <!-- 왼쪽 절반: 맵 -->
    <!-- 지도 표시 -->
    <div class="map-container" id="map"></div>
    <!-- 오른쪽 찜한 여행지 목록 컨테이너 스타일링 -->
    <div class="favorite-places-container">
      <div v-if="likedAttractions.length === 0">
        <p>찜한 여행지가 없습니다.</p>
      </div>
      <div v-else>
        <div v-for="place in likedAttractions" :key="place.id" class="favorite-place">
          <div class="image-container">
            <img :src="place.firstImage" alt="여행지 이미지">
          </div>
          <div class="info-container">
            <h3 class="favorite-link">
              <span @click="moveDetail(place.contentId)">{{ place.title }}</span>
              <button class="favorite-btn" @click="deleteAttractionLikeDetail(place.likeNo)">
                <i class="fas fa-heart"></i>
              </button>
            </h3>
            <p class="address">
              <span class="address-line">{{ place.addr1 }}</span>
              <span class="address-line">{{ place.addr2 }}</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Font Awesome 사용을 위한 CSS */
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css');


/* 전체 컨테이너 스타일링 */
.main-container {
  display: flex;
  padding: 10px; /* Adjusted padding for the main container */
}

/* 왼쪽 맵 컨테이너 스타일링 */
.map-container {
  flex: 1;
  width: 650px;
  height: 700px;
  margin: 5%;
  border: 3px solid #010101ac; /* Add a green border to the map container */
}

.map-container img {
  width: 100%;
  height: auto;
}

/* 오른쪽 찜한 여행지 목록 컨테이너 스타일링 */
.favorite-places-container {
  flex: 1;
  margin-top: 5%;
  padding: 10px;
}

/* 찜한 여행지 항목 스타일링 */
.favorite-place {
  display: flex;
  width: 563px;
  height: 200px;
  margin-bottom: 10%; /* Adjusted margin for the favorite place */
  padding: 5px; /* Adjusted padding for the favorite place */
}

/* info-container 스타일링 */
.info-container {
  flex: 2;
  padding: 0 10px; /* Adjusted padding for the info container */
}


/* 주소 스타일링 */
.address {
  font-size: 16px;
  color: #000000;
  margin-left: 10%;
  margin-top: 10%;
}

.address-line {
  display: block;
  margin-bottom: 3px;
}

.image-container {
  flex: 1;
}

.image-container img {
  width: 175px;
  height: 200px;
	box-shadow:0px 4px 4px rgba(0, 0, 0, 0.25);
	background-color:rgba(196.00000351667404, 196.00000351667404, 196.00000351667404, 1);
	width:175px;
	height:200px;
	border-top-left-radius:15px;
	border-top-right-radius:15px;
	border-bottom-left-radius:15px;
	border-bottom-right-radius:15px;
	background-repeat:no-repeat;
	background-size:cover;
	border:1px solid rgba(0, 0, 0, 1);
}

.info-container {
  flex: 2;
  padding: 0 10px; /* Adjusted padding for the info container */
}

.favorite-link {
  font-family: 'Roboto', sans-serif; /* Add this line to set the font to Roboto */
  width: 348px;
  height: 36px;
  margin-left: 50px;
  display: flex;
  align-items: center;
}

.favorite-btn {
  background-color: white;
  color: #dd1414;
  border: none;
  padding: 2px;
  cursor: pointer;
  position: absolute; /* Enable absolute positioning for the button */
  top: 20; /* Position the button at the vertical center of the container */
  right: 0; /* Position the button at the right edge of the container */
  transform: translateY(-12%); /* Adjust to vertically center the button */
}

.favorite-btn i {
  margin-right: 2px;
  font-size: 20px;
  line-height: 20px;
}

/* 추가적인 스타일링이 필요하다면 여기에 추가하세요 */
</style>

