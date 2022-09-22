<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="entidade">
        <h2 class="jh-entity-heading" data-cy="entidadeDetailsHeading">
          <span v-text="$t('akipGenerationWebApp.entidade.detail.title')">Entidade</span> {{ entidade.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('akipGenerationWebApp.entidade.name')">Name</span>
          </dt>
          <dd>
            <span>{{ entidade.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.entidade.fields')">Fields</span>
          </dt>
          <dd>
            <span>{{ entidade.fields }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.entidade.relations')">Relations</span>
          </dt>
          <dd>
            <span>{{ entidade.relations }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.entidade.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('akipGenerationWebApp.TypeEntity.' + entidade.type)">{{ entidade.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.entidade.process')">Process</span>
          </dt>
          <dd>
            <div v-if="entidade.process">
              <router-link :to="{ name: 'ProcessView', params: { processId: entidade.process.id } }">{{ entidade.process.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.entidade.application')">Application</span>
          </dt>
          <dd>
            <div v-if="entidade.application">
              <router-link :to="{ name: 'ApplicationView', params: { applicationId: entidade.application.id } }">{{
                entidade.application.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="entidade.id" :to="{ name: 'EntidadeEdit', params: { entidadeId: entidade.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./entidade-details.component.ts"></script>
