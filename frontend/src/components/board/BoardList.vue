<template>
  <div class="container">
    <div style="display: flex; align-items: center; justify-content: space-between; width: 100%;">
      <!-- 가운데 배치 -->
      <h4 style="margin: 0; flex-grow: 1; text-align: center;">내 게시글 목록</h4>
      
      <!-- 오른쪽 배치 -->
      <RouterLink class="board-write" style="text-align: right; margin-left: auto;" :to="{ name: 'boardCreate' }">게시글 작성</RouterLink>
    </div>
    <BoardSearchInput />
    <hr />
    <table class="table table-hover text-center">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>조회수</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(board, index) in currentPageBoardList" :key="board.id">
          <td>{{ (index+1)+(currentPage-1)*4 }}</td>
          <td>
            <RouterLink :to="`/board/${board.id}`" class="board">{{board.title}}</RouterLink>
          </td>
          <td>{{ board.writer }}</td>
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
import BoardSearchInput from './BoardSearchInput.vue';

const store = useBoardStore();

const searchInfo2 = ref({
    key: "none",
    word: "",
    orderBy: "created",
    orderByDir: "desc",
});
onMounted(() => {
  store.searchBoardList(searchInfo2.value);
});

const currentPage = ref(1)
const perPage = 4;
const pageCount = computed(()=>{
    return Math.ceil(store.boardList.length / perPage)
})
const clickPage = function(page){
    currentPage.value = page
}

const currentPageBoardList = computed(()=>{
    return store.boardList.slice((currentPage.value-1)*perPage, currentPage.value*perPage)
})


</script>

<style scoped>
.board-write {
  color: black;
  text-decoration: none;  
}
.board-write:hover {
    color: black;
    text-decoration: underline; 
}
.board {
    color: black;
    text-decoration: none; 
    
}
.board:hover {
    color: black;
    text-decoration: underline; 
}
</style>
