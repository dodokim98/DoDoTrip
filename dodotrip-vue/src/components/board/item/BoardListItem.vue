<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="text-center">
    <!-- 관리자 -->
    <template v-if="article.memberUserEmail === 'admin@mail.com'">
      <th style="color: red">공지&#128680;</th>
      <td class="text-start">
        <router-link
          :to="{
            name: 'article-view',
            params: { articleno: article.articleNo },
          }"
          class="article-title link-dark"
        >
          {{ article.subject }}
        </router-link>
      </td>
      <td>관리자</td>
      <td>{{ article.hit }}</td>
      <td>{{ article.registerTime }}</td>
    </template>

    <!-- 사용자 -->
    <template v-else>
      <th scope="row">{{ article.articleNo }}</th>
      <td class="text-start">
        <router-link
          :to="{
            name: 'article-view',
            params: { articleno: article.articleNo },
          }"
          class="article-title link-dark"
        >
          {{ article.subject }}
        </router-link>
      </td>
      <td>{{ article.memberUserEmail }}</td>
      <td>{{ article.hit }}</td>
      <td>{{ article.registerTime }}</td>
    </template>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
