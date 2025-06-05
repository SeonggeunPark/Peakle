<template>
  <div class="container">
    <h4>리뷰 목록</h4>
    <!-- <BoardSearchInput /> -->
    <hr />
    <table class="table table-hover text-center">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>쓰니</th>
          <th>조회수</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="review in currentPageReviewList" :key="review.reviewId">
          <td>{{ review.id }}</td>
          <td>
            <RouterLink :to="`/review/${review.id}`">{{review.title}}</RouterLink>
          </td>
          <td>{{ review.writer }}</td>
          <td>{{ review.viewCnt }}</td>
          <td>{{ new Date(review.created).toISOString().substring(0, 10) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useReviewStore } from '@/stores/review';
import { computed, onMounted } from 'vue';

const store = useReviewStore();
onMounted(() => {
    store.getReviewList();
})

const currentPageReviewList = computed(()=> {
    return store.reviewList;
})

</script>

<style scoped>

</style>