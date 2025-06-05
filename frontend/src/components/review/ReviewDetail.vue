<template>
    <div class="container">
        <h4>리뷰 상세</h4>
        <hr />
        <div class="d-flex justify-content-center">
            <div class="card" style="width: 30rem">
                <div class="card-body">
                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <h4 class="card-title">{{ store.review.title }}</h4>
                        <div class="d-flex justify-content-end">
                            <h6 class="card-subtitle mx-3 text-body-secondary">{{ store.review.writer }}</h6>
                            <h6 class="card-subtitle text-body-secondary">{{ store.review.created }}</h6>
                        </div>
                    </div>
                    <p class="card-text ">
                        {{ store.review.content }}
                    </p>
                    <div class="d-flex justify-content-center">
                        <button class="mx-3 btn btn-outline-success" @click="moveUpdate">수정</button>
                        <button class="mx-3 btn btn-outline-danger" @click="deleteBoard">삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useReviewStore } from '@/stores/review';
import axios from 'axios';
import { onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';


const route = useRoute();
const router = useRouter();
const store = useReviewStore();

onMounted(() => {
    store.getReview(route.params.id);
})

const moveUpdate = function () {
    router.push('/review/update');
};

const deleteBoard = function () {
    const check = window.confirm("정말 삭제하시겠습니까?");
    if (check) {
        axios
            .delete(`http://localhost:8080/review/${route.params.id}`)
            .then(() => {
                alert("리뷰가 삭제되었습니다.")
                router.push({ name: "reviews" });
            })
            .catch(() => { });
    }
};

</script>

<style lang="scss" scoped>

</style>