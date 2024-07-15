<template>
  <div class="app-component-container">
    <span id="title" class="app-component-text">{{ text }}</span>
    <div class="app-component-container1">
      <div class="app-component-container2">
        <div class="app-component-container3">
          <span class="app-component-text1">{{ text1 }}</span>
          <input
            type="text"
            id="expenses_input"
            autofocus="true"
            :placeholder="textinputPlaceholder1"
            class="app-component-textinput input"
          />
        </div>
        <button
          id="sort_by_name"
          type="button"
          class="app-component-button button"
        >
          {{ button }}
        </button>
      </div>
    </div>
    <div class="app-component-container4">
      <div class="app-component-container5 list-item">
        <div class="app-component-container6">
          <DangerousHTML
            html="<table width=”100%” border=”1'>
  
  <tr>
  <th>Amount</th>
  <th>Category</th>
  <th>Place</th>
  <th>Date</th>
  </tr>
  
  <tr>
  <td>1</td>
  <td>2</td>
  <td>3</td>
  <td>4</td>
  </tr>
  
  <tr>
  <td>1</td>
  <td>2</td>
  <td>3</td>
  <td>4</td>
  </tr>
  
  </table>
  
  <style>
  
   table {
    width: 100%;
    border: 2px solid;
  }
  
  th, tr, td {
    padding: 15px;
    text-align: left;
    border: 2px solid;  
    height: 10%;
  }
  </style>"
          ></DangerousHTML>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DangerousHTML from 'dangerous-html/vue'

export default {
  name: 'AppComponent',
  props: {
    button: {
      type: String,
      default: 'Amount sort',
    },
    text1: {
      type: String,
      default: 'Expense input:',
    },
    textinputPlaceholder1: {
      type: String,
      default: 'placeholder',
    },
    text: {
      type: String,
      default: 'Expenses storage',
    },
  },
  components: {
    DangerousHTML,
  },
}
</script>

<style scoped>
.app-component-container {
  width: 100%;
  height: 1046px;
  display: flex;
  position: relative;
  align-items: center;
  flex-direction: column;
  justify-content: flex-start;
}
.app-component-text {
  width: auto;
  height: auto;
  align-self: center;
  font-style: normal;
  margin-top: var(--dl-space-space-unit);
  text-align: center;
  font-family: "Arial";
  font-weight: 700;
  line-height: 3;
  margin-bottom: var(--dl-space-space-unit);
  letter-spacing: 3px;
  text-transform: uppercase;
}
.app-component-container1 {
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: flex-start;
}
.app-component-container2 {
  width: 100%;
  height: 701px;
  display: flex;
  align-items: flex-start;
  justify-content: flex-end;
}
.app-component-container3 {
  display: flex;
  align-self: center;
}
.app-component-text1 {
  align-self: center;
  margin-top: var(--dl-space-space-threeunits);
  line-height: 2;
  border-color: #0909e7;
  border-width: 2px;
  letter-spacing: 1px;
}
.app-component-textinput {
  a: 10px;
  width: 273px;
  height: 93px;
  margin: var(--dl-space-space-twounits);
  padding: var(--dl-space-space-unit);
  align-self: center;
  margin-top: var(--dl-space-space-fourunits);
  line-height: 2;
}
.app-component-button {
  align-self: flex-end;
  margin-top: var(--dl-space-space-unit);
  text-align: center;
  margin-left: var(--dl-space-space-unit);
  margin-right: var(--dl-space-space-unit);
  margin-bottom: var(--dl-space-space-unit);
}
.app-component-container4 {
  flex: 0 0 auto;
  width: auto;
  height: auto;
  display: flex;
  align-items: flex-start;
  justify-content: center;
}
.app-component-container5 {
  flex: 1;
  width: 1196px;
  height: 192px;
  align-self: center;
  padding-left: var(--dl-space-space-halfunit);
  padding-right: var(--dl-space-space-halfunit);
}
.app-component-container6 {
  display: contents;
}
</style>
