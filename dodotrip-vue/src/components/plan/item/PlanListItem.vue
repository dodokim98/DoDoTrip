<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
defineProps({ plan: Object });

</script>


<template>
  <tr class="text-center">
    <th scope="row">{{ plan.planNo }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'plan-view', params: { planno: plan.planNo } }"
        class="article-title link-dark"
      >
        {{ plan.title }}
      </router-link>
    </td>
    <td>{{ plan.startDate }}</td>
    <td>{{ plan.endDate }}</td>
    <td>{{ plan.createDate }}</td>
  </tr>
</template>


<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
a {
  text-decoration: none;
}

.article-title {
  text-align: center;
}
</style>
