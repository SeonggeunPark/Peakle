<template>
    <div>
        <div class="text-start"><h4>주간 클라이머 TOP 5 <i class="bi bi-trophy-fill text-warning"></i></h4></div>
        <div class="container text-center" :v-if="top5Users.length > 0">
            <div class="row gx-0">
                <!-- 여기에 v-for -->
                <div class="col container text-center" v-for="(user, index) in top5Users" :key="user.userId">
                    <div class="profile-container">
                        <img :src="`/img/character/${user.userId%10}.png`" class="profile">
                        <img :src="`/img/medal/gold.png`" class="medal " v-if="index==0">
                        <img :src="`/img/medal/silver.png`" class="medal " v-if="index==1">
                        <img :src="`/img/medal/bronze.png`" class="medal " v-if="index==2">
                    </div>
                    <div class="user-name">
                        {{ user.userName }}
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script setup>
import { useMyRecordStore } from '@/stores/myRecord';
import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';

const recordStore = useMyRecordStore();
const userStore = useUserStore();
const rankedUsers = ref([]); // 정렬된 데이터
const top5Users = ref([]); // 상위 5명의 유저 데이터

// 최근 7일 데이터 불러오기
onMounted(async () => {
    try {
        const userRecords = await recordStore.getMyRecordDate(0, 7);

        // 모든 사용자 정보를 가져오기
        await userStore.getAllUsers();
        const allUsers = userStore.allUsers // 모든 사용자 정보가 들어있는 객체 또는 배열

        if (userRecords) {
            // 유저별로 데이터를 합산하기 위해 Map을 사용
            const userMap = new Map();

            userRecords.forEach((record) => {
                const userId = record.userId;

                // 문제 풀이 횟수 계산
                const problemCounts = record.record.split(', ').reduce((sum, item) => {
                    const count = parseInt(item.split(': ')[1]);
                    return sum + (isNaN(count) ? 0 : count);
                }, 0);

                if (!userMap.has(userId)) {
                    // userId와 매칭되는 사용자 이름 찾기
                    const user = allUsers.find(u => u.id === userId);
                    const userName = user ? user.name : 'Unknown User';

                    userMap.set(userId, {
                        userId: userId,
                        userName: userName,
                        recordCount: 1,
                        problemCount: problemCounts,
                    });
                } else {
                    const userData = userMap.get(userId);
                    userData.recordCount += 1;
                    userData.problemCount += problemCounts;
                }
            });

            // 유저 데이터를 배열로 변환 후 정렬
            rankedUsers.value = Array.from(userMap.values()).sort((a, b) => {
                if (b.recordCount === a.recordCount) {
                    return b.problemCount - a.problemCount; // 기록 수가 같다면 문제 풀이 횟수로 정렬
                }
                return b.recordCount - a.recordCount; // 기록 수로 정렬
            });

            // 상위 5명의 유저를 top5Users에 저장
            top5Users.value = rankedUsers.value.slice(0, 5);
        }
    } catch (error) {
        console.error('데이터를 불러오는 중 오류 발생:', error);
    }
});



</script>

<style scoped>
.col {
    padding: 0px;
    width: 10px;
}
.profile-container {
    position: relative;
    display: inline-block;
    width: 50px;
    height: 50px;
}

.profile {
    position: relative;
    height: 100%;
    width: 100%;
    z-index: 1; /* 프로필 이미지를 위에 두기 위한 z-index */
}

.medal {
    position: absolute;
    top: 15px;
    left: 14px;
    width: 110%;
    height: 110%;
    z-index: 2; /* 메달 이미지를 위에 두기 위한 z-index */
}

.user-name {
    margin-top: 10px;
    font-weight: bold;
}
</style>