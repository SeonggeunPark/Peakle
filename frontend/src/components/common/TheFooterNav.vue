<template>
    <footer class="footer">
      <div class="footer-content">
        <!-- 로고 -->
        <div class="footer-logo">
          <img src="/img/logo/logo_literal_black.png" alt="Logo" />
          <p class="footer-tagline">Peaceful Climbing</p>
        </div>
        <!-- 정보 -->
        <div class="footer-info">
          <p>팀명 피클 | SSAFY 12기 대전 3반 | 주소 대전 유성구 동서대로 98-39, 삼성화재 유성캠퍼스</p>
          <p>이메일 ssafy@ssafy.com | 고객센터: 010-0000-0000 (상담 어려움)</p>
        </div>
      </div>
      <hr>
      <!-- 저작권 -->
      <div class="footer-copyright">
        Copyright © 2024 SPIRI7. All Rights Reserved.
      </div>
    </footer>
  </template>
  
  <script setup>
  // JavaScript는 필요하지 않습니다.
  </script>
  
  <style scoped>
  .footer {
    background-color: white;
    color: #555;
    padding: 15px 20px;
    border-top: 1px solid #ddd;
  
    /* 로고와 정보 섹션 */
    .footer-content {
      display: flex;
      align-items: center; /* 로고와 정보 수직 중앙 정렬 */
      gap: 20px; /* 로고와 정보 사이 여백 */
      margin-bottom: 10px; /* 저작권 정보와의 간격 */
    }
  
    /* 로고 스타일 */
    .footer-logo {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      text-align: center;
    }
  
    .footer-logo img {
      max-width: 80px; /* 로고 크기 제한 */
      height: auto;
    }
  
    .footer-tagline {
      margin-top: 5px;
      font-size: 12px;
      color: #777;
    }
  
    /* 정보 텍스트 스타일 */
    .footer-info {
      font-size: 14px;
      line-height: 1.4;
      color: #555;
      text-align: left;
  
      p {
        margin: 0;
        margin-bottom: 5px; /* 텍스트 간격 */
        font-weight: 400;
        font-size: 9.6px;
      }
    }
  
    /* 저작권 스타일 */
    .footer-copyright {
      text-align: center;
      font-size: 12px;
      color: #aaa;
    }
  }
  </style>
  