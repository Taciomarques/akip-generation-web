<template>
  <div>
    <div class="card" v-if="akipFields">
      <h5 class="card-header">
        <span class="title" v-text="$t('akipGenerationWebApp.akipField.fieldsDomain')"></span>
      </h5>
      <div class="card-body">
        <div class="card">
          <div class="table-responsive">
            <table class="table table-striped table-sm">
              <thead>
                <tr>
                  <th>
                    <div class="form-group form-check pb-2">
                      <input
                        type="checkbox"
                        :disabled="readOnly"
                        class="form-check-input"
                        v-on:click="selectAllAkipFields()"
                        v-model="selectedAllAkipFields"
                      />
                    </div>
                  </th>
                  <th v-text="$t('akipGenerationWebApp.akipField.name')"></th>
                  <th v-text="$t('akipGenerationWebApp.akipField.type')"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(akipField, index) in akipFields" :key="index">
                  <td>
                    <div class="form-group form-check">
                      <input :disabled="readOnly" type="checkbox" class="form-check-input" v-model="akipField.selected" />
                    </div>
                  </td>
                  <td>
                    <span v-text="akipField.fieldName"></span>
                  </td>
                  <td>
                    <span v-text="akipField.fieldType"></span>
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
          v-on:click="$emit('akip-fields-selecteds', JSON.parse(JSON.stringify(akipFields.filter(akipField => akipField.selected))))"
          :disabled="akipFields.filter(akipField => akipField.selected).length == 0 || readOnly"
          class="btn btn-success float-right mb-3"
          data-cy="addFields"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;<span v-text="$t('akipGenerationWebApp.akipField.addFields')" />
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-fields-select.component.ts"></script>
