<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getPlansByUser } from "@/api/plan";
import { useMemberStore } from "@/stores/member";
import PlanListItem from "./item/PlanListItem.vue";

const memberStore = useMemberStore();
const { getUserInfo } = memberStore;

const router = useRouter();
const userName = ref("");
const planList = ref([]);


onMounted(async () => {
  let token = sessionStorage.getItem("accessToken"); //현재 로그인한 유저
  const userInfo = await getUserInfo(token); //유저의 role 권한 얻기 위해 메소드 호출
  console.log(userInfo.role);
  userName.value = userInfo.userName;
  getPlanList();
});

const getPlanList = () => {
  console.log("서버에서 글목록 얻어오자!!!");
  getPlansByUser(
    ({ data }) => {
      console.log(data);
      planList.value = data;
      console.log(planList.value);
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 text-center">
            나의 여행 계획
        </h2>
      </div>
      <div class="col-lg-10">
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">시작일</th>
              <th scope="col">종료일</th>
              <th scope="col">생성일</th>
            </tr>
          </thead>
          <tbody>
            <PlanListItem
              v-for="plan in planList"
              :key="plan.planNo"
              :plan="plan"
            ></PlanListItem>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
.container {
  width: 100%;
}

h2 {
  background-color: #343a40; /* Dark gray background for the title */
  color: #f8f9fa; /* Light text color for the title */
  padding: 10px;
  border-radius: 5px;
}
</style>
