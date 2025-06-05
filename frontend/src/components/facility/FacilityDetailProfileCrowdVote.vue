<template>
    <div>
        <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#exampleModal"><i class="bi bi-clipboard-check"></i> 혼잡도
            평가</button>
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
            data-bs-backdrop="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">혼잡도 투표</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                            @click="closeModal"></button>
                    </div>
                    <div class="modal-body">
                        <p>현재 상태를 선택해주세요</p>
                        <div class="justify-content-between">
                            <button class="btn btn-outline-danger mx-4 px-4" @click="castVote('혼잡')"
                                v-if="evaluate != '혼잡'">혼잡</button>
                            <button class="btn btn-danger mx-4 px-4" @click="castVote('혼잡')" v-else>혼잡</button>
                            <button class="btn btn-outline-warning mx-4 px-4" @click="castVote('평균')"
                                v-if="evaluate != '평균'">평균</button>
                            <button class="btn btn-warning mx-4 px-4" @click="castVote('평균')" v-else>평균</button>
                            <button class="btn btn-outline-success mx-4 px-4" @click="castVote('원활')"
                                v-if="evaluate != '원활'">원활</button>
                            <button class="btn btn-success mx-4 px-4" @click="castVote('원활')" v-else>원활</button>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" @click="sendCrowd">평가 등록</button>
                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"
                            @click="closeModal">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useFacilityStore } from '@/stores/facility';
import { useUserStore } from '@/stores/user';
import { ref } from 'vue';
const store = useFacilityStore();
const evaluate = ref('');
const userStore = useUserStore();

const showModal = ref(false);

const openVoteModal = () => {
    showModal.value = true;
}

const closeModal = () => {
    showModal.value = false;
}
const sendCrowd = () => {
    showModal.value = false;
    let user = null;
    if (userStore.loginUser != null) {
        user = userStore.loginUser;
        store.crowdCreate(1, user.id, evaluate.value, user.manager);
    } else {
        alert('로그인을 해야 합니다!')
    }
}

const castVote = (vote) => {
    evaluate.value = vote;
}
</script>

<style scoped></style>