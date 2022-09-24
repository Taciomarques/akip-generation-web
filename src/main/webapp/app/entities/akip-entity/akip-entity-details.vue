<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="akipEntity">
        <h2 class="jh-entity-heading" data-cy="entidadeDetailsHeading">
          <span v-text="$t('akipGenerationWebApp.akipEntity.detail.title')">Entidade</span> {{ akipEntity.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipEntity.name')">Name</span>
          </dt>
          <dd>
            <span>{{ akipEntity.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipEntity.fields')">Fields</span>
          </dt>
          <dd>
            <span>{{ akipEntity.fields }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipEntity.relations')">Relations</span>
          </dt>
          <dd>
            <span>{{ akipEntity.relations }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipEntity.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('akipGenerationWebApp.TypeEntity.' + akipEntity.type)">{{ akipEntity.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipEntity.process')">Process</span>
          </dt>
          <dd>
            <div v-if="akipEntity.process">
              <router-link :to="{ name: 'ProcessView', params: { akipProcessId: akipEntity.process.id } }">{{
                akipEntity.process.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('akipGenerationWebApp.akipEntity.application')">Application</span>
          </dt>
          <dd>
            <div v-if="akipEntity.application">
              <router-link :to="{ name: 'ApplicationView', params: { akipApplicationId: akipEntity.application.id } }">{{
                akipEntity.application.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="akipEntity.id"
          :to="{ name: 'AkipEntityEdit', params: { akipEntityId: akipEntity.id } }"
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

<script lang="ts" src="./akip-entity-details.component.ts"></script>
