<template>
  <div class="home">
    <div class="card border-primary">
      <div class="card-body">
        <div class="container text-center">
          <h1 class="display-4" v-text="$t('global.title')"></h1>
          <div>
            <div class="alert alert-warning" v-if="!authenticated">
              <span v-text="$t('global.messages.info.authenticated.prefix')">If you want to </span>
              <a class="alert-link" v-on:click="openLogin()" v-text="$t('global.messages.info.authenticated.link')">sign in</a>
            </div>
            <div class="alert alert-warning" v-if="!authenticated">
              <span v-text="$t('global.messages.info.register.noaccount')">You don't have an account yet?</span>&nbsp;
              <router-link class="alert-link" to="/register" v-text="$t('global.messages.info.register.link')"
                >Register a new account
              </router-link>
            </div>
            <div class="alert alert-success" v-if="authenticated">
              <span v-text="$t('global.welcome', { param: $store.getters.account.firstName })">Welcome</span>&nbsp;
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
