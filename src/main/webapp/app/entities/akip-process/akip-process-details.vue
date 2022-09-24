<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="akipProcess">
        <h2 class="jh-entity-heading" data-cy="processDetailsHeading">
          <span v-text="$t('akipGenerationWebApp.process.detail.title')">Process</span> {{ akipProcess.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipProcess.name')">Name</span>
          </dt>
          <dd>
            <span>{{ akipProcess.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipProcess.percentageExecuted')">Percentage Executed</span>
          </dt>
          <dd>
            <span>{{ akipProcess.percentageExecuted }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipProcess.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('akipGenerationWebApp.StatusProcess.' + akipProcess.status)">{{ akipProcess.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipProcess.application')">Application</span>
          </dt>
          <dd>
            <div v-if="akipProcess.application">
              <router-link :to="{ name: 'AkipApplicationView', params: { akipApplicationId: akipProcess.application.id } }">{{
                akipProcess.application.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="akipProcess.id"
          :to="{ name: 'AkipProcessEdit', params: { akipProcessId: akipProcess.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./akip-process-details.component.ts"></script>
