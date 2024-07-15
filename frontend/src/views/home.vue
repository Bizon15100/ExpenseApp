<template>
  <div class="home-container"></div>
</template>

<script>
export default {
  name: 'Home',
  props: {},
  metaInfo: {
    title: 'Ethical Brave Flamingo',
    meta: [
      {
        property: 'og:title',
        content: 'Ethical Brave Flamingo',
      },
    ],
  },
}
</script>

<style scoped>
.home-container {
  width: 100%;
  display: flex;
  min-height: 100vh;
  align-items: center;
  flex-direction: column;
  justify-content: flex-end;
}
</style>
