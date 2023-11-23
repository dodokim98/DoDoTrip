<script setup>
import { ref, onMounted, watch } from "vue";
import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { insertPlan } from "@/api/plan";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const plan = ref({
  planNo: 0,
  createTime: "",
  startDate: "",
  endDate: "",
  title: "",
  content: "",
  memberUserEmail: "",
});

const from = ref(new Date());
const to = ref(new Date());

const formatDate = (dateRef) => {
  const date = dateRef.value;
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const titleErrMsg = ref("");

watch(
  () => plan.value.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      titleErrMsg.value = "제목을 확인해 주세요!!!";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else {
    plan.value.startDate = formatDate(from);
    plan.value.endDate = formatDate(to);
    registPlan();
  }
}

function registPlan() {
  console.log("여행등록", plan.value);

  insertPlan(
    plan.value,
    (response) => {
      let msg = "여행 등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "여행 등록이 완료되었습니다.";
      alert(msg);
      console.log("Created Plan ID in registPlan:", response.data.planNo);
      movePlanWrite(response.data.planNo);
    },
    (error) => console.error(error)
  );
}

function movePlanWrite(data) {
  console.log("여행계획 세우기 이동");
  router.push({ name: "plan-write", params: { planno: data } });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="centered-content">
      <label for="title" style="margin-right: 10px">여행 제목:</label>
      <input v-model="plan.title" id="title" type="text" />
    </div>
    <div class="date-input">
      <label for="from">From :</label>
      <VueDatePicker v-model="from" id="from" />
    </div>

    <div class="date-input">
      <label for="to">To :</label>
      <VueDatePicker v-model="to" id="to" />
    </div>

    <div class="button-container">
      <button type="submit" class="btn btn-outline-primary mb-3">
        여행 등록
      </button>
    </div>

    <hr />
  </form>
</template>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}

div {
  text-align: center;
}

/* 제목 입력 필드 스타일 조정 */
#title {
  width: 100%;
  padding: 8px;
  margin-top: 8px;
  align-items: center;
  box-sizing: border-box;
}

.date-input {
  margin-bottom: 20px;
  margin-top: 20px;
}
</style>
