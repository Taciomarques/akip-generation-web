/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EntidadeDetailComponent from '@/entities/entidade/entidade-details.vue';
import EntidadeClass from '@/entities/entidade/entidade-details.component';
import EntidadeService from '@/entities/entidade/entidade.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Entidade Management Detail Component', () => {
    let wrapper: Wrapper<EntidadeClass>;
    let comp: EntidadeClass;
    let entidadeServiceStub: SinonStubbedInstance<EntidadeService>;

    beforeEach(() => {
      entidadeServiceStub = sinon.createStubInstance<EntidadeService>(EntidadeService);

      wrapper = shallowMount<EntidadeClass>(EntidadeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { entidadeService: () => entidadeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEntidade = { id: 123 };
        entidadeServiceStub.find.resolves(foundEntidade);

        // WHEN
        comp.retrieveEntidade(123);
        await comp.$nextTick();

        // THEN
        expect(comp.entidade).toBe(foundEntidade);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEntidade = { id: 123 };
        entidadeServiceStub.find.resolves(foundEntidade);

        // WHEN
        comp.beforeRouteEnter({ params: { entidadeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.entidade).toBe(foundEntidade);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
