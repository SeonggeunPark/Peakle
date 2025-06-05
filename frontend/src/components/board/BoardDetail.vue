<template>
    <div class="container text-start">
        <div class="title">
            {{ store.board.title }}
        </div>
        <div class="writer-info d-flex ">
            <div class="profile me-3">
                <img :src="`/img/character/${store.board.userId%11}.png`" alt="">
            </div>
            <div>
                <p class="my-0">
                    {{ store.board.writer }}
                </p>
                <p>
                    {{ store.board.created }}
                </p>
            </div>
        </div>
        <hr>
        <div class="content my-5">
            {{ store.board.content }}
        </div>

        <hr>
        <BoardReviewList :boardId="parseInt(route.params.id)" />
    </div>
</template>
  
<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { useBoardStore } from "@/stores/board";
import axios from "axios";
import BoardReviewList from "./BoardReviewList.vue";
import { useUserStore } from "@/stores/user";
const route = useRoute();
const router = useRouter();
const store = useBoardStore();
const userStore = useUserStore();

let today = new Date();

onMounted(() => {
    store.getBoard(route.params.id);
});

const moveUpdate = function () {
    router.push({ name: "boardUpdate" });
};

const deleteBoard = function () {
    axios
        .delete(`http://localhost:8080/board/${route.params.id}`)
        .then(() => {
            router.push({ name: "boardList" });
        })
        .catch(() => { });
};

const isOner = function (boardId) {
    return boardId === userStore.loginUser?.id;
}
</script>
  
<style scoped>
.profile {
    width: 40px;
    height: 40px;
    border-radius: 100%;
    /* border-style: solid; */
    /* border-width: 1px; */
    /* border-color: gra; */
    /* box-shadow: 1px 1px 1px 1px gray; */
    overflow: hidden;
    background-color: #e4e9e9;
}
img {
    width: 100%;
    height: 100%;
}
.title {
    font-size: 40px;
    font-weight: 500;
}
.writer-info {
    font-size: 15px;
}
</style>
  