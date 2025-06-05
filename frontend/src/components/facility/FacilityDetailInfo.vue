<template>

    <div>
        <div style="color: #71C9CE; border-style: solid; margin-bottom: 9px;"></div>
        <div class="container text-start mb-4" v-if="facility">
            <h5>주소</h5>
            <p>{{ facility.address }}</p>
        </div>
        <div id="title" class="container text-start mb-3" v-if="facilityFeature">
            <h5>시설 정보</h5>
            <div class="d-flex">
                    <div class="icon" v-if="facilityFeature.bouldering == 1">
                        <img src="/public/img/facility/boulder.png">
                        <span>볼더링</span>
                    </div>
                    <div class="icon" v-if="facilityFeature.lead == 1">
                        <img src="/public/img/facility/leads.png">
                        <span>리드</span>
                    </div>
                    <div class="icon" v-if="facilityFeature.footwash == 1">
                        <img src="/public/img/facility/foot.png">
                        <span>세족시설</span>
                    </div>
                    <div class="icon" v-if="facilityFeature.bodywash == 1">
                        <img src="/public/img/facility/shower.png">
                        <span>샤워실</span>
                    </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useFacilityStore } from '@/stores/facility';

const facilStore = useFacilityStore();
const props = defineProps({
    facilityFeature: Object,
    facility : Object
})
</script>

<style scoped>
h5 {
    font-weight: bolder;
}

img {
    width: 24%;
    margin-right: 2px;
    display:block; 
    width: 55px;
}
.icon {
    margin-right: 2px;
    text-align: center;
}
span {
    font-size: small;
    font-weight: 900;
}
</style>