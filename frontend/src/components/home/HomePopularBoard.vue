<template>
    <div class="mb-4">
        <div class="d-flex justify-content-between spacetext-start mb-3">
            <h4>인기글 <i class="bi bi-fire text-danger"></i></h4>
            <RouterLink :to="{ name: 'boardList' }" class="move-detail align-self-end mx-5">더보기 ▶</RouterLink>
        </div>
        <div class="recent-board container m-0 p-0" v-if="top3Boards.length > 0">
                <div class="board text-start mx-3" v-for="(board, index) in top3Boards" :key="board.id">
                    <RouterLink :to="`/board/${board.id}`" class="title m-0 p-0">{{board.title}}</RouterLink>
                    <p class="content fw-lighter"> {{board.content}}</p>
                    <hr v-if="index != 2">
                </div>
            </div>
    </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { onMounted, ref, watch, watchEffect } from 'vue';
const boardStore = useBoardStore();
const top3Boards = ref([]);

// 최근 게시글 3개 불러오기
onMounted(async () => {
    try {
        const searchInfo = {
            key: "none",
            word: "",
            orderBy: "view_cnt",
            orderByDir: "desc",
        };
        // 게시글 목록 가져오기
        
        await boardStore.getPopularBoard(searchInfo.value);
        // boardStore.boardList는 이미 반응형 변수이기 때문에 watchEffect로 추적 가능
    } catch (error) {
        console.error('데이터를 불러오는 중 오류 발생:', error);
    }
});

// boardStore.boardList 변경 감지 후 top3Boards 업데이트
watchEffect(() => {
    const boardList = boardStore.popularBoard;
    if (boardList && boardList.length > 0) {
        top3Boards.value = boardList.slice(0, 3);
    }
});


</script>

<style scoped>
.content {
    font-weight: 300; /* 텍스트 두께를 좀 더 가볍게 */
    font-size: medium;
    color: #6c757d; /* 더 연한 회색으로 설정 (부트스트랩의 secondary 텍스트 색상) */
    opacity: 0.7; /* 글자 투명도를 낮춰서 더 흐리게 보이게 함 */
}
.title {
    color: black;
    text-decoration: none; 
    
}
.title:hover {
    color: black;
    text-decoration: underline; 
}
.move-detail {
    color: black;
    text-decoration: none;  
}
</style>