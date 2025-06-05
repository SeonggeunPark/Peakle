<template>
    <div class="container">
        <h4>리뷰 수정</h4>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="title" placeholder="제목" v-model="store.review.title">
            <label for="title">제목</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="writer" placeholder="작성자" readonly v-model="store.review.writer">
            <label for="writer">작성자</label>
        </div>
        <div class="form-floating mb-3">
            <textarea class="form-control" id="content" placeholder="내용" style="height: 200px"
                v-model="store.review.content"></textarea>
            <label for="content">내용</label>
        </div>
        <div class="d-flex justify-content-end">
            <button class="btn btn-outline-success mx-3" @click="updateReview">수정</button>
            <button class="btn btn-outline-danger" @click="backButton">뒤로</button>
        </div>
    </div>
</template>

<script setup>
import { useReviewStore } from '@/stores/review';
import { useRouter } from 'vue-router';

const store = useReviewStore();
const router = useRouter();

const updateReview = function () {
    store.updateReview();
    router.replace({name:"review", params: {id: store.review.id}})
}
const backButton = function () {
    router.go(-1);
}

</script>

<style lang="scss" scoped>

</style>