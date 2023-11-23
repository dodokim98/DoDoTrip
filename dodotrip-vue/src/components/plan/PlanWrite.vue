<script setup>
import NewCalendar from "./item/NewCalendar.vue";
import PlanList from "./PlanList.vue";
</script>

<template>
  <div class="container">
    <div class="main">
      <NewCalendar />
      <PlanList />
    </div>
    <RouterView />
  </div>
</template>

<style scoped>

.main {
  display: flex; /* 자식 컨테이너도 가로로 나열합니다. */
  gap: 20px; /* 컴포넌트 간의 간격을 조절합니다. */
  margin-top: 20px; /* 컨테이너 상단에 마진을 추가하여 위로 조금 띄웁니다. */
}

</style>
