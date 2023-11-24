<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import KakaoMap from "@/components/attraction/KakaoMap.vue";
import { useAttractionPlanStore } from "@/stores/attraction";
import { insertAttractionPlan } from "@/api/plan";

const attStore = useAttractionPlanStore();
const route = useRoute();
const router = useRouter();
const { selectedTrips, removeTrip } = attStore;

const { planno } = route.params; //plan_plan_no
const oneDay = ref(new Date()); //plan_date

const formatDate = (dateRef) => {
  const date = dateRef.value;
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  return `${year}-${month}-${day}`;
};
const index = 0;

//여행 계획 저장(Plan DB)
function registAttraction() {
  oneDay.value = formatDate(oneDay);

  for (var i = 0; i < selectedTrips.length; i++) {
    const attractionInfo = selectedTrips[i];
    console.log(attractionInfo);

    const planAttraction = ref({
      planAttractionNo: 0,
      order: i + 1,
      attractionInfoContentId: attractionInfo.contentId,
      planPlanNo: planno,
      planDate: oneDay.value,
    });
    insertAttractionPlan(
      planno,
      planAttraction.value,
      (response) => {
        let msg = "여행지 등록 처리시 문제 발생했습니다.";
        if (response.status == 201) msg = "여행지 등록이 완료되었습니다.";
        alert(msg);
      },
      (error) => console.error(error)
    );
  }
  router.replace({ name: "plan" });
}
</script>

<template>
  <div class="container d-flex justify-content-evenly">
    <div class="row">
      <div class="col-2">
        <div class="left-container">
          <h2>Selected Trips</h2>
          <ul>
            <li v-for="(trip, index) in selectedTrips" :key="index">
              {{ trip.title }}
              <button @click="removeTrip(index)">삭제</button>
            </li>
          </ul>

          <button @click="registAttraction" class="btn btn-primary">
            여행계획 저장
          </button>
        </div>
      </div>

      <div class="col-8">
        <div class="map-container col-6">
          <KakaoMap />
        </div>
      </div>

      <div class="col-2">
        <div class="calendar-container">
          <label for="oneDay">날짜 선택</label>
          <VueDatePicker v-model="oneDay" id="oneDay" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  margin-top: 15px;
}

.left-container {
  flex: 1;
}

.map-container {
  flex: 2;
  width: 100%;
}

.calendar-container {
  flex: 1;
}

.btn-primary {
  margin-top: 8px;
}

/* 여행지 목록 스타일 조정 */
ul {
  list-style: none;
  padding: 0;
}

li {
  margin-bottom: 8px;
}

/* 버튼 스타일 조정 */
button {
  margin-left: 8px;
  cursor: pointer;
}

/* 다른 스타일이 필요한 경우 추가적으로 조정 가능 */
</style>
