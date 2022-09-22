<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="process">
        <h2 class="jh-entity-heading" data-cy="processDetailsHeading">
          <span v-text="$t('akipGenerationWebApp.process.detail.title')">Process</span> {{ process.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('akipGenerationWebApp.process.name')">Name</span>
          </dt>
          <dd>
            <span>{{ process.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.process.percentageExecuted')">Percentage Executed</span>
          </dt>
          <dd>
            <span>{{ process.percentageExecuted }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.process.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('akipGenerationWebApp.StatusProcess.' + process.status)">{{ process.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.process.application')">Application</span>
          </dt>
          <dd>
            <div v-if="process.application">
              <router-link :to="{ name: 'ApplicationView', params: { applicationId: process.application.id } }">{{
                process.application.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="process.id" :to="{ name: 'ProcessEdit', params: { processId: process.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./process-details.component.ts"></script>
