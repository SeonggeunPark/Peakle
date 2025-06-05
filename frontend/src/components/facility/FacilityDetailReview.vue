<template>
    <div>
        <div style="color: #71C9CE; border-style: solid; margin-bottom: 7px;"></div>
        <div class="review-header d-flex align-items-center">
            <h5 >리뷰 {{ selectedfacilReviews.length }}</h5>
            <div class="rating-overview ms-auto mb-4">
                <p class="review-rating mb-0">{{ averageRating }} / 5</p>
                <ReviewCreate :facility="facility"/>
            </div>
        </div>  
        <p class="text-start">리뷰를 남기면 나의 체험으로 모두에게 표시됩니다. 다른 클라이머들을 위해 리뷰를 남겨주세요.</p>
        <hr />
        <!-- 리뷰 리스트 -->
        <div v-for="review in selectedfacilReviews" :key="review.id" class="review-item p-3 mb-3">
            <div class="d-flex align-items-center">
                <img :src="`/img/character/${review.userId%11}.png`" alt="User Image" class="user-image rounded-circle" />
                <div class="ms-3">
                    <strong>{{ review.writer }}</strong>
                    <span class="text-muted">{{ selectedfacilReviews.created }}</span>
                </div>
            </div>
            <div class="mt-2">
                <span class="review-stars" v-for="star in review.rating">★</span>
                <span class="review-stars" v-for="star in 5-review.rating">☆</span>
                <p class="review-text mt-1 text-start">{{ review.content }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useFacilityStore } from '@/stores/facility';
import { useReviewStore } from '@/stores/review';
import { computed, onMounted, ref } from 'vue';
import ReviewCreate from '../review/ReviewCreate.vue';

const facilStore = useFacilityStore();
const reviewStore = useReviewStore();
const props = defineProps({
    facility: Object,
    selectedfacilReviews : Object
})

const averageRating = computed(()=> {
    if (reviewStore.facilReviews.length === 0) return '0.0'

    let sum = 0;
    let validCount = 0;
    for (let i=0; i<reviewStore.facilReviews.length; i++) {
        sum += reviewStore.facilReviews[i].rating
        validCount += 1;
    }
    return Math.round((sum/validCount)*10)/10
})
</script>

<style scoped>
h5 {
    font-weight: bolder;
}
.facility-review-container {
    background-color: #ffffff;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.review-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.review-rating {
    font-size: 2rem;
    font-weight: bold;
    color: #ffab00;
}

.review-item {
    background-color: #f8f9fa;
    border-radius: 8px;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.05);
}

.user-image {
    width: 50px;
    height: 50px;
    object-fit: cover;
}

.review-stars {
    font-size: 1.2rem;
    color: #ffd700;
}

.review-text {
    font-size: 1rem;
    color: #333;
}
</style>