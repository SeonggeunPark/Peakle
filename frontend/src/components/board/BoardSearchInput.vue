<template>
    <div class="text-center">
        <div class="row align-items-center">
            <div class="col-2">
                <select class="form-select" v-model="searchInfo.key">
                    <option value="none">전체</option>
                    <option value="writer">작성자</option>
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                </select>
            </div>
            <div class="col-6">
                <input type="text" class="form-control" v-model="searchInfo.word" />
            </div>
            <div class="col-1"></div>
            <div class="col-2">
                <!-- 정렬 기준 선택 -->
                <select class="form-select" v-model="visibleOrderBy" @change="updateOrder">
                    <option value="none" disabled>정렬 기준 선택</option>
                    <option value="new">최신순</option>
                    <option value="old">오래된 순</option>
                    <option value="many">조회수 많은 순</option>
                    <option value="few">조회수 적은 순</option>
                </select>
            </div>
            <div class="col-1">
                <button class="btn btn-outline-warning" @click="searchBoardList">검색</button>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref } from "vue";
import { useBoardStore } from "@/stores/board";

const store = useBoardStore();
const searchInfo = ref({
    key: "none",
    word: "",
    orderBy: "none",
    orderByDir: "asc",
});
const searchBoardList = function () {
    store.searchBoardList(searchInfo.value);
};

const visibleOrderBy = ref("none");

// 정렬 기준 변경 함수
const updateOrder = function () {
  switch (visibleOrderBy.value) {
    case "new": // 최신순
      searchInfo.value.orderBy = "created";
      searchInfo.value.orderByDir = "desc";
      break;
    case "old": // 오래된 순
      searchInfo.value.orderBy = "created";
      searchInfo.value.orderByDir = "asc";
      break;
    case "many": // 조회수 많은 순
      searchInfo.value.orderBy = "view_cnt";
      searchInfo.value.orderByDir = "desc";
      break;
    case "few": // 조회수 적은 순
      searchInfo.value.orderBy = "view_cnt";
      searchInfo.value.orderByDir = "asc";
      break;
    default:
      searchInfo.value.orderBy = "none";
      searchInfo.value.orderByDir = "asc";
      break;
  }
};
</script>
  
<style scoped></style>
  