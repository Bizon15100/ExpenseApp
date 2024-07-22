<template>
  <div class="app-component-container">
    <span id="title" class="app-component-text">{{ text }}</span>
    <template>
      <section id="form">
        <form class="form-addExpense" style="padding: 10px; background-color: #ebebeb;" data-dashlane-rid="0e78da42bc484ac8" data-form-type="login">
          <h1 class="h3 mb-3 font-weight-normal">Please input expense</h1>
          <label for="inputAmount" class="sr-only">Amount</label>
          <input type="text" id="amount" class="form-control" placeholder="Amount" required="" autofocus=""
                 data-dashlane-rid="f5111088d29d6f41" data-form-type="amount"
                 v-model="amount" data-kwimpalastatus="alive" data-kwimpalaid="1673381633908-0">
          <label for="inputCategory" class="sr-only">Category</label>
          <input type="text" id="category" class="form-control" placeholder="Category" required=""
                 data-dashlane-rid="cc9963121605ad01" data-form-type="category"
                 v-model="category" data-kwimpalastatus="alive" data-kwimpalaid="1673381633908-1">
          <label for="inputPlace" class="sr-only">Place</label>
          <input type="text" id="place" class="form-control" placeholder="Place" required=""
                 data-dashlane-rid="cc9963121605ad02" data-form-type="place" data-kwimpalastatus="alive"
                 v-model="place" data-kwimpalaid="1673381633908-2">
          <label for="inputDate" class="sr-only">Date</label>
          <input type="date" id="date" class="form-control" placeholder="Date" required=""
                 data-dashlane-rid="cc9963121605ad03" data-form-type="date" data-kwimpalastatus="alive"
                 v-model="date" data-kwimpalaid="1673381633908-3">
          <button class="btn btn-lg btn-primary btn-block" type="submit" @click="addExpense()" data-dashlane-label="true" data-dashlane-rid="39bbb57564a936e3" data-form-type="action,addExpense" data-kwimpalastatus="dead">OK</button> <br>
        </form>
      </section>
    </template>
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
import axios from "axios";

export default {

  data() {
    return {
      amount:'',
      category:'',
      place:'',
      date:'',
    }
  },
  methods: {
    async addExpense() {
      try {
        await axios.post(
            'http://localhost:8081/add',
            {
              amount: this.amount,
              place: this.place,
              category: this.category,
              date: this.date
            }
        );
        alert(`Dodano wydatek`);
      } catch (error) {
        alert("Niepoprawne dane");
      }
    }
  },


  name: 'AppComponent',
  props: {
    button: {
      type: String,
      default: 'Amount sort',
    },
    OK: {
      type: String,
      default: 'OK',
    },
    text1: {
      type: String,
      default: 'Expense input:',
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

#confirm_button {
  FONT-WEIGHT: 500;
  border: #191818 solid;
  width: 10% !important;
  height: 10% !important;
  align-self: center;
  margin-top: var(--dl-space-space-twounits);
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
