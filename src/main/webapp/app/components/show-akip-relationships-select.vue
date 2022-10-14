<template>
  <div>
    <div class="card" v-if="akipRelationships && akipRelationships.length > 0">
      <h5 class="card-header">
        <span class="title" v-text="$t('akipGenerationWebApp.akipRelationship.relationshipsDomain')"></span>
      </h5>
      <div class="card-body">
        <div class="card">
          <div class="table-responsive">
            <table class="table table-striped table-sm">
              <thead>
                <tr>
                  <th></th>
                  <th v-text="$t('akipGenerationWebApp.akipRelationship.relationshipName')"></th>
                  <th v-text="$t('akipGenerationWebApp.akipRelationship.relationshipType')"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(akipRelationship, index) in akipRelationships" :key="index">
                  <td>
                    <b-form-checkbox :disabled="readOnly" v-model="akipRelationship.selected" />
                  </td>
                  <td>
                    <span v-text="akipRelationship.relationshipName"></span>
                  </td>
                  <td>
                    <span v-text="akipRelationship.relationshipType"></span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="container">
        <button
          type="submit"
          v-on:click="
            $emit('akip-relationships-selecteds', JSON.parse(JSON.stringify(akipRelationships.filter(akipField => akipField.selected))))
          "
          :disabled="akipRelationships.filter(akipField => akipField.selected).length == 0 || readOnly"
          class="btn btn-success btn-sm float-right mb-3"
          data-cy="addRelationships"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;<span
            v-text="$t('akipGenerationWebApp.akipRelationship.addRelationships')"
          />
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-relationships-select.component.ts"></script>
