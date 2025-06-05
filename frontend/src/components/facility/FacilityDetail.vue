<template>
    <div>
        <FacilityDetailProfile class="facility-profile" id="profile" :facility="selectedFacility.facility" />
        <hr>
        <div class="facility-basic-info">
            <FacilityDetailInfo id="info" :facility-feature="selectedFacility.facilityFeature"
                :facility="selectedFacility.facility" />
        </div>
        <hr>
        <div class="facility-review">
            <FacilityDetailReview id="review" :facility="selectedFacility.facility" :selectedfacilReviews="selectedfacilReviews" />
        </div>
    </div>
</template>

<script setup>
import { useFacilityStore } from '@/stores/facility';
import FacilityDetailInfo from './FacilityDetailInfo.vue';
import FacilityDetailProfile from './FacilityDetailProfile.vue';
import FacilityDetailReview from './FacilityDetailReview.vue';

const facilStore = useFacilityStore();
const props = defineProps({
    selectedFacility: Object,
    selectedfacilReviews : Object
})
</script>

<style scoped>
#profile {
    background-color: white;
}

#info {
    background-color: white;
}

#review {
    background-color: white;
}

.facility-profile {
    background-color: #f8f9fa;
    border-radius: 8px;
    overflow: auto;
}
.facility-basic-info {
    background-color: #f8f9fa;
    border-radius: 8px;
    overflow: auto;
}

.facility-review {
    background-color: #f8f9fa;
    border-radius: 8px;
    overflow: auto;

}
</style>
