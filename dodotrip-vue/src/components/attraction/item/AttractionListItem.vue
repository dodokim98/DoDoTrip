<script setup>
import { ref, onMounted } from "vue";
import { useAttractionPlanStore } from "@/stores/attraction";
defineProps({ place: Object });

const store = useAttractionPlanStore();
const { addTrip } = store;

</script>

<template>
  <tr class="text-center">
    <td>
      <img
        :src="place.firstImage"
        alt="대표이미지"
        style="width: 50px; height: 50px"
      />
    </td>
    <td>{{ place.title }}</td>
    <td>{{ place.addr }}</td>
    <td>{{ place.latitude }}</td>
    <td>{{ place.longitude }}</td>

    <!-- 추가 버튼 -->
    <td>
      <button @click="addTrip(place)" class="btn btn-primary">추가</button>
    </td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
