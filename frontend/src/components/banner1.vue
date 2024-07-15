<template>
  <div class="banner1-container thq-section-padding">
    <div class="banner1-max-width thq-section-max-width">
      <div class="banner1-container1">
        <h2 class="banner1-title thq-heading-2">{{ heading1 }}</h2>
        <h3 class="banner1-text thq-heading-3">{{ content1 }}</h3>
      </div>
      <button type="button" class="thq-button-filled">{{ action1 }}</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Banner1',
  props: {
    heading1: {
      type: String,
      default: 'Company Name',
    },
    action1: {
      type: String,
      default: 'Call to Action',
    },
    content1: {
      type: String,
      default: 'Company mission statement',
    },
  },
}
</script>

<style scoped>
.banner1-container {
  gap: var(--dl-space-space-unit);
  display: flex;
  position: relative;
  align-items: center;
}
.banner1-max-width {
  gap: var(--dl-space-space-oneandhalfunits);
  display: flex;
  align-items: center;
  flex-direction: column;
}
.banner1-container1 {
  gap: var(--dl-space-space-halfunit);
  display: flex;
  align-items: center;
  flex-direction: column;
}
.banner1-title {
  text-align: center;
}
.banner1-text {
  text-align: center;
}
</style>
