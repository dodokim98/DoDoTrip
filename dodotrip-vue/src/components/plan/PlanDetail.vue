<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { viewPlan } from "@/api/plan";

const route = useRoute();
const router = useRouter();

const { planno } = route.params;

const plans = ref({});

onMounted(() => {
    getPlan();
});

const getPlan = () => {
  viewPlan(planno, ({ data }) => {
    console.log(data);
    plans.value = data;
  },
    (error) => {
      console.log(error)
    });
};

</script>

<template>
  <div class="plan-details">
    <div class="attractions">
      <h3>여행지 목록</h3>
      <ul class="attraction-list">
        <li v-for="attraction in plans" :key="attraction.planAttractionNo" class="attraction-item">
          <div class="attraction-info">
            <p><strong>날짜:</strong> {{ attraction.planDate }}</p>
            <p><strong>순서:</strong> {{ attraction.order }}</p>
            <p><strong>이름:</strong> {{ attraction.title }}</p>
            <p><strong>주소:</strong> {{ attraction.addr1 }} {{ attraction.addr2 }}</p>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
.attractions {
  text-align: center;
}

.attraction-list {
  list-style: none;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.attraction-item {
  border: 1px solid #ccc;
  margin: 8px;
  padding: 12px;
}

.attraction-info {
  text-align: left;
}
</style>
