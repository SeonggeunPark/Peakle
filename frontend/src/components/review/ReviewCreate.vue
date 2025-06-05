<template>
    <div>
        <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#exampleModal1">
            <i class="bi bi-pencil-square"></i> 작성
        </button>
        <div class="modal fade" id="exampleModal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
            data-bs-backdrop="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">리뷰 작성</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                            @click="closeModal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="title" placeholder="제목" v-model="review.title">
                            <label for="title">제목</label>
                        </div>
                        <div class="form-floating mb-3">
                            <textarea class="form-control" id="content" placeholder="내용" style="height: 200px"
                                v-model="review.content"></textarea>
                            <label for="content">내용</label>
                        </div>
                        <div class="form-floating mb-3">
                            <label class="rating-label">별점</label>
                            <div class="rating-container">
                                <div class="rating-stars">
                                    <span v-for="star in 5" :key="star" class="star"
                                        :class="{ active: star <= review.rating }" @click="setRating(star)">
                                        ★
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" data-bs-target="#exampleModalToggle2" data-bs-dismiss="modal" @click="createReview">리뷰 등록</button>
                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"
                            @click="closeModal">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useReviewStore } from "@/stores/review";

const props = defineProps({
    facility: Object,
});

const review = ref({
    userId: "",
    facilityId: "",
    title: "",
    content: "",
    writer: "",
    rating: 0,
});

const store = useReviewStore();

// 별점 설정 함수
const setRating = (rating) => {
    review.value.rating = rating;
};

// 리뷰 생성 함수
const createReview = function () {
    const user = JSON.parse(localStorage.getItem('user'))['loginUser'];
    review.value.userId = user.id;
    review.value.facilityId = props.facility.id; // props.selectedFacility로 수정
    review.value.writer = user.name;

    store.registReview(review.value);
    closeModal(); // 리뷰 등록 후 모달 닫기
};

// 모달 닫기 함수
const closeModal = () => {
    review.value = {
        userId: "",
        facilityId: "",
        title: "",
        content: "",
        writer: "",
        rating: 0,
    };
};
</script>

<style scoped>
.rating-stars {
    font-size: 24px;
    cursor: pointer;
}

.star.active {
    color: gold;
}
</style>