<template>
    <div v-if="facility">
        <div class="picture d-flex align-items-center mb-1">
            <img :src="facility.img" class="img-fluid" />
        </div>

        <div class="d-flex justify-content-between">
            <div id="facil-name" class="container text-start">
                <h3>
                    {{ facility.name }}
                </h3>
            </div>
        </div>

        <!-- <div class="container text-start mb-3" v-if="currentFacility">
            <span v-if="currentFacility.crowd < 1.5" style="color: green;">여유</span>
            <span v-else-if="1.5 <= currentFacility.crowd && currentFacility.crowd < 2.5" style="color: orange;">보통</span>
            <span v-else-if="currentFacility.crowd >= 2.5" style="color: red;">혼잡</span>
            <span v-else>최근 혼잡도 평가 없음</span>
        </div> -->
        <div class="d-flex">
            <div class="container text-mid m-0 p-0 align-self-center" v-if="currentFacility">
                <img v-if="currentFacility.crowd < 1.5" class="crowd_img" src="/img/crowd/crowd_low.png"></img>
                <img v-else-if="1.5 <= currentFacility.crowd && currentFacility.crowd < 2.5" class="crowd_img"
                    src="/img/crowd/crowd_mid.png"></img>
                <img v-else-if="currentFacility.crowd >= 2.5" class="crowd_img" src="/img/crowd/crowd_high.png"></img>
                <span v-else style="color: gray; font-weight: 300; font-size: 15px;">최근 혼잡도 평가 없음</span>
            </div>
    
            <div id="facil-likes" class="container m-0 p-0">
                <!-- <button v-if="isLike" type="button" class="btn btn-outline-danger" @click="toggleLike"><i class="bi bi-heart-fill"></i> 찜</button> -->
                <button v-if="!isLike" type="button" class="like btn btn-outline-danger mb-1" @click="addLike"><i
                        class="bi bi-heart"></i> 찜</button>
                <button v-else type="button" class="like btn btn-danger mb-1" @click="removeLike"><i
                        class="bi bi-heart-fill"></i>
                    찜</button>
                <FacilityDetailProfileCrowdVote />
            </div>
        </div>
    </div>
</template>

<script setup>
import { useFacilityStore } from '@/stores/facility';
import { computed, onMounted, ref, watch } from 'vue';
import { useLikeStore } from '@/stores/like';
import FacilityDetailProfileCrowdVote from './FacilityDetailProfileCrowdVote.vue';
import { useUserStore } from '@/stores/user';

const facilStore = useFacilityStore();
const likeStore = useLikeStore();
const userStore = useUserStore();
const props = defineProps({
    facility: Object
})

// 현재 facility와 일치하는 facility 데이터를 가져오기
const currentFacility = computed(() => {
    return facilStore.facilList.find(facil => facil.id === props.facility.id);
});

const addLike = async function () {
    // 로컬스토리지에 저장된 user가 있으면 정상 처리
    // if (user != null) {
    //     await likeStore.addLike(user.id, props.facility.id)
    if (userStore.loginUser != null) {
        await likeStore.addLike(userStore.loginUser?.id, props.facility.id)
        isLike.value = true
    }
    // 없으면 불가 메세지 (로그인해야합니다.)
    else {
        alert('로그인을 해야 합니다!')
    }
}

const removeLike = async function () {
    // 로컬스토리지에 저장된 user가 있으면 정상 처리
    // if (user != null) {
    //     await likeStore.removeLike(user.id, props.facility.id)
    if (userStore.loginUser != null) {
        await likeStore.removeLike(userStore.loginUser?.id, props.facility.id)
        isLike.value = false
    }
    // 없으면 불가 메세지 (로그인해야합니다.)
    else {
        alert('로그인을 해야 합니다!')
    }
}

const isLike = ref(false)
const checkIfLiked = async () => {
    if (userStore.loginUser != null) {
        await likeStore.getLikes(userStore.loginUser?.id);
        // 현재 facility가 유저의 찜 목록에 있는지 확인
        isLike.value = likeStore.likeList.some(like => like.facilityId === props.facility.id);
    }
}

watch(() => props.facility, async (newFacility, oldFacility) => {
    if (newFacility && newFacility !== oldFacility) {
        await checkIfLiked();
    }
}, { deep: true, immediate: true });
onMounted(() => {
    checkIfLiked();
});
</script>

<style scoped>
.picture {
    width: 100%;
    height: 10rem;
    overflow: hidden;
}

h3 {
    font-weight: 500;
    font-size: 24px;
}

.crowd_img {
    height: 60%;
    width: 60%;
}

.like {
    width: 86%;
}
</style>