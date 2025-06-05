<template>
  <div class="container">
    <h4>내 운동 기록</h4>
    <!-- <BoardSearchInput /> -->
    <hr />
    <table class="table table-hover text-center">
      <thead>
        <tr>
          <th>제목</th>
          <th>운동한 날짜</th>
        </tr>
      </thead>
      <tbody>
        <!-- 아래의 tr 태그를 반복 -->
        <tr v-for="record in currentPageRecordList" :key="record.id">
          <td>
            <RouterLink :to="`/myrecord/${record.id}`">{{record.title}}</RouterLink>
          </td>
          <td>{{ record.created }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useMyRecordStore } from '@/stores/myRecord';
import { computed, onMounted } from 'vue';

const store = useMyRecordStore();
onMounted(() => {
    store.getMyRecordList();
})

const currentPageRecordList = computed(()=> {
    return store.recordList;
})

</script>

<style scoped>

h4, thead {
  font-family: 'buttonTitle';
}

</style>