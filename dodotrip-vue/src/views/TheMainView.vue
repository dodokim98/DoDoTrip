<script setup>
import { ref } from "vue";
</script>

<template>
  <div>
    <!-- Main Image Section -->
    <div class="main-image">
      <img src="@/assets/mainImage1.jpg" alt="Main Image">
      <div class="image-text">
        <h2 class="main-title">모험의 시작, 함께 떠나는 여정</h2>
        <p class="main-description">새로운 경험, 새로운 기억, 함께하는 여행</p>
      </div>
    </div>

    <!-- Feature Sections -->
    <div class="feature-sections">
      <!-- 1. 지역별 관광지 -->
      <div class="feature-item">
        <img src="@/assets/locationIcon.png" alt="Location Icon">
        <h3><router-link :to="{ name: 'attraction' }" class="nav-link">지역별 관광지</router-link></h3>
        <p>여러 지역의 아름다운 관광지를 탐험해보세요.</p>
      </div>

      <!-- 2. 나의 여행 계획 -->
      <div class="feature-item">
        <img src="@/assets/planningIcon.png" alt="Planning Icon">
        <h3><router-link :to="{ name: 'plan' }" class="nav-link">나의 여행계획</router-link></h3>
        <p>자신만의 특별한 여행 계획을 만들어보세요.</p>
      </div>

      <!-- 3. 여행 정보 공유 -->
      <div class="feature-item">
        <img src="@/assets/shareIcon.png" alt="Share Icon">
        <h3><router-link :to="{ name: 'board' }" class="nav-link">여행정보공유</router-link></h3>
        <p>여행 경험을 다른 이용자들과 공유해보세요.</p>
      </div>

      <!-- 4. 찜한 여행지 -->
      <div class="feature-item">
        <img src="@/assets/heartIcon.png" alt="Heart Icon">
         <h3><router-link :to="{ name: 'attraction-like' }" class="nav-link">찜한 여행지</router-link></h3>
        <p>맘에 드는 여행지를 찜하고 저장하세요.</p>
      </div>

      <!-- 5. QnA -->
      <div class="feature-item">
        <img src="@/assets/qnaIcon.png" alt="QnA Icon">
        <h3><router-link :to="{ name: 'qna' }" class="nav-link">QnA</router-link></h3>
        <p>다른 여행자들과 궁금한 것을 나눠보세요.</p>
      </div>
    </div>
  </div>
</template>


<style scoped>
/* 스타일링을 추가하세요 */
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap');

header {
  text-align: center;
  padding: 20px;
  background-color: #333;
  color: white;
}

.main-image {
  position: relative;
}

.main-image img {
  width: 100%;
  height: 500px;
}

.image-text {
  position: absolute;
  top: 50%;
  left: 20%;
  transform: translate(-50%, -50%);
  text-align: center;
  color: beige;
}

.main-title {
  font-family: 'Noto Sans KR', sans-serif;
  font-size: 2em;
  margin-bottom: 10px;
}

.main-description {
  font-family: 'Noto Sans KR', sans-serif;
  font-size: 1.2em;
}

.travel-plans {
  display: flex;
  overflow-x: auto;
}

.plan-item {
  flex: 0 0 300px;
  margin: 10px;
}

footer {
  text-align: center;
  padding: 20px;
  background-color: #333;
  color: white;
}

/* Feature Sections 스타일 */
.feature-sections {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin: 20px;
}

.feature-item {
  text-align: center;
  max-width: 200px;
  margin: 10px;
}

.feature-item img {
  width: 50px;
  height: 50px;
  margin-bottom: 10px;
}
</style>
