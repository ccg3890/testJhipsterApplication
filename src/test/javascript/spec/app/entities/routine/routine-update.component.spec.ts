/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RoutineUpdateComponent from '@/entities/routine/routine-update.vue';
import RoutineClass from '@/entities/routine/routine-update.component';
import RoutineService from '@/entities/routine/routine.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Routine Management Update Component', () => {
    let wrapper: Wrapper<RoutineClass>;
    let comp: RoutineClass;
    let routineServiceStub: SinonStubbedInstance<RoutineService>;

    beforeEach(() => {
      routineServiceStub = sinon.createStubInstance<RoutineService>(RoutineService);

      wrapper = shallowMount<RoutineClass>(RoutineUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          routineService: () => routineServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.routine = entity;
        routineServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(routineServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.routine = entity;
        routineServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(routineServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRoutine = { id: 'ABC' };
        routineServiceStub.find.resolves(foundRoutine);
        routineServiceStub.retrieve.resolves([foundRoutine]);

        // WHEN
        comp.beforeRouteEnter({ params: { routineId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.routine).toBe(foundRoutine);
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
