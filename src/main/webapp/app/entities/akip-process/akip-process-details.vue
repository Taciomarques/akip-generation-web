<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div class="card border-primary" v-if="akipProcess">
        <div class="card-header">
          <div class="d-flex">
            <div>
              <h2 class="jh-entity-heading" data-cy="processDetailsHeading">
                <span v-text="$t('akipGenerationWebApp.akipProcess.detail.title')">Process</span> {{ akipProcess.id }}
              </h2>
            </div>
            <div class="p-1 ml-3">
              <show-akip-process-status :value="akipProcess.status"></show-akip-process-status>
            </div>
          </div>
        </div>
        <div class="card-body">
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
                <router-link :to="{ name: 'AkipApplicationView', params: { akipApplicationId: akipProcess.application.id } }"
                  >{{ akipProcess.application.name }}
                </router-link>
              </div>
            </dd>
          </dl>
        </div>
        <div class="card-footer">
          <div class="container">
            <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
              <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
            </button>
            <!--            <router-link-->
            <!--              v-if="akipProcess.id"-->
            <!--              :to="{ name: 'AkipProcessEdit', params: { akipProcessId: akipProcess.id } }"-->
            <!--              custom-->
            <!--              v-slot="{ navigate }"-->
            <!--            >-->
            <!--              <button @click="navigate" class="btn btn-primary float-right">-->
            <!--                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>-->
            <!--              </button>-->
            <!--            </router-link>-->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./akip-process-details.component.ts"></script>
