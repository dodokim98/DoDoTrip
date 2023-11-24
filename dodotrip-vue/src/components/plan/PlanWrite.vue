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
  display: flex; 
  gap: 20px;
  margin-top: 20px; 
}

</style>
