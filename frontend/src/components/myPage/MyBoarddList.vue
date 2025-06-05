<template>
  <div class="container">
    <div style="display: flex; align-items: center; justify-content: space-between; width: 100%;">
      <!-- 가운데 배치 -->
      <h4 style="margin: 0; flex-grow: 1; text-align: center;">내 게시글 목록</h4>
    </div>
    
    <BoardSearchInput />
    <hr />
    <table class="table table-hover text-center">
      <thead>
        <tr>
          <th>제목</th>
          <th>조회수</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="board in currentPageBoardList" :key="board.id">
          <td>
            <RouterLink :to="`/board/${board.id}`">{{board.title}}</RouterLink>
          </td>
          <td>{{ board.viewCnt }}</td>
          <td>{{ board.created }}</td>
        </tr>
      </tbody>
    </table>
    <nav aria-label="Page navigation">
      <ul class="pagination d-flex justify-content-center">
        <li class="page-item"><a class="page-link" @click.prevent="currentPage--" :class="{disabled: currentPage <= 1}" href="#">&lt;</a></li>
        <li class="page-item" :class="{active: currentPage == page}" v-for="page in pageCount" :key="page">
            <a href="#" class="page-link" @click.prevent="clickPage(page)">{{page}}</a>
        </li>
        <li class="page-item"><a class="page-link" @click.prevent="currentPage++" :class="{disabled: currentPage >= pageCount}" href="#">&gt;</a></li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { computed, onMounted, ref } from 'vue';
import BoardSearchInput from '../board/BoardSearchInput.vue';
import { useUserStore } from '@/stores/user';

const store = useBoardStore();
const userStore = useUserStore();
const myBoardList = ref([]);

onMounted(() => {
  store.getBoardList();
});

const currentPage = ref(1)
const perPage = 4;
const pageCount = computed(()=>{
    return Math.ceil(myBoardList.value.length / perPage)
})
const clickPage = function(page){
    currentPage.value = page
}

const currentPageBoardList = computed(()=>{
  if (store.boardList.length>0) {
      myBoardList.value = store.boardList.filter((item) => item.userId === userStore.loginUser?.id);
    }
    return myBoardList.value.slice((currentPage.value-1)*perPage, currentPage.value*perPage)
})


</script>

<style scoped></style>