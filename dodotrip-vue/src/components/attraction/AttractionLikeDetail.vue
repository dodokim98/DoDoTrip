<script setup>
import { onMounted, onUnmounted } from 'vue';
import { useAttractionDetailStore } from '@/stores/attractionDetail.js';
import { storeToRefs } from 'pinia';

const attractionDetailStore = useAttractionDetailStore();
const { updatedTripListItem } = storeToRefs(attractionDetailStore);

onMounted(() => {
  console.log("updatedTripListItem :", updatedTripListItem.value);
});

// 페이지가 언마운트 될 때 세션 스토리지에 데이터 저장
onUnmounted(() => {
  // 만약 updatedTripListItem이 배열이라면 별도로 맞춰서 저장해야 합니다.
  const updatedTripListArray = Array.isArray(updatedTripListItem.value)
    ? updatedTripListItem.value.map(item => ({ ...item }))
    : null;

  sessionStorage.setItem('updatedTripListItem', JSON.stringify(updatedTripListArray));
});
</script>

<template>
  <div>
      <div class="image-section">
        <div v-for="place in updatedTripListItem" :key="place.contentId" class="img-container">
          <img v-if="place.overview" :src="place.firstImage" alt="이미지">
          <div class="info">
            <h3 v-if="place.overview" id="title">{{ place.title }}</h3>
            <div v-if="place.overview" class="detail">
              <span  class="detailInfo">상세정보</span>
            </div>
            <p v-if="place.overview">{{ place.overview }}</p>
            <div v-if="place.overview" class="detail">
              <span  class="detailInfo">주소</span>
            </div>
            <p v-if="place.overview">{{ place.addr }}</p>
          </div>
        </div>
      </div>
    </div>
</template>

<style scoped>  
.detail{
    width: 940;
    height: 76px;
    border-bottom: 1px solid rgb(218, 214, 214);
}
.detailInfo { 
    color:rgba(51.00000075995922, 51.00000075995922, 51.00000075995922, 1);
    width:84.52999877929688px;
    height:31px;
    font-family:Noto Sans KR;
    margin-top: 10%;
    text-align:left;
    font-size:23.625px;
    letter-spacing:-1;
    line-height:px;
}

#title{
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>
