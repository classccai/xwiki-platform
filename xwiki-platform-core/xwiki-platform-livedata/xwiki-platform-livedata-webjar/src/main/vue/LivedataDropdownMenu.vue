<!--
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 -->


<!--
  LivedataDropdownMenu is a component that propose different actions
  to the user: chaging layout, opning advance configuration panels, ...
  It should be included once in every layout component,
  generaly on the leftmost of its topbar so that it stay a consistent place.
-->
<template>
  <!--
    The Livedata Dropdown
    Uses the Bootstrap 3 dropdown syntax.
  -->
  <div class="livedata-dropdown-menu btn-group">

    <!-- Drowpdown open / close button-->
    <a
      class="btn btn-default dropdown-toggle"
      :title="$t('livedata.dropdownMenu.title')"
      data-toggle="dropdown"
      aria-haspopup="true"
      aria-expanded="true"
      role="button"
    >
      <span class="fa fa-ellipsis-v"></span>
    </a>

    <!-- Drowpdown body -->
    <ul class="dropdown-menu dropdown-menu-right">

      <!-- Actions -->
      <li class="dropdown-header">{{ $t('livedata.dropdownMenu.actions') }}</li>

      <li>
        <!-- Refresh -->
        <a href="#" @click.prevent="logic.updateEntries()">
          <span class="fa fa-repeat"></span> {{ $t('livedata.action.refresh') }}
        </a>
      </li>


      <!-- Layouts -->
      <li role="separator" class="divider"></li>

      <li class="dropdown-header">{{ $t('livedata.dropdownMenu.layouts') }}</li>

      <!-- Layout options -->
      <li
        v-for="layout in data.meta.layouts"
        :key="layout.id"
        :class="{
          'disabled': logic.currentLayoutId === layout.id,
        }"
      >
        <a href="#" @click.prevent="logic.changeLayout(layout.id)">
          <XWikiIcon :icon-descriptor="layout.icon"></XWikiIcon>
          {{ layout.name }}
        </a>
      </li>

      <!-- Panels -->
      <li role="separator" class="divider"></li>

      <li class="dropdown-header">{{ $t('livedata.dropdownMenu.panels') }}</li>

      <!-- Properties Panel -->
      <li>
        <a href="#" @click.prevent="logic.uniqueArrayToggle(logic.openedPanels, 'propertiesPanel')">
          <span class="fa fa-list-ul"></span> {{ $t('livedata.dropdownMenu.panels.properties') }}
        </a>
      </li>

      <!-- Sort Panel -->
      <li>
        <a href="#" @click.prevent="logic.uniqueArrayToggle(logic.openedPanels, 'sortPanel')">
          <span class="fa fa-sort"></span> {{ $t('livedata.dropdownMenu.panels.sort') }}
        </a>
      </li>

      <!-- Filter Panel -->
      <li>
        <a href="#" @click.prevent="logic.uniqueArrayToggle(logic.openedPanels, 'filterPanel')">
          <span class="fa fa-filter"></span> {{ $t('livedata.dropdownMenu.panels.filter') }}
        </a>
      </li>

    </ul>

  </div>
</template>


<script>
import XWikiIcon from "./utilities/XWikiIcon.vue";

export default {

  name: "LivedataDropdownMenu",

  components: {
    XWikiIcon,
  },

  inject: ["logic"],

  computed: {
    data () { return this.logic.data; },
  },

};
</script>


<style>

.livedata-dropdown-menu .btn-default {
  background-color: #f8f8f8;
  background-image: none;
  border-color: #e5e5e5;
  box-shadow: none;
  color: #333333;
  text-shadow: none;
}

.livedata-dropdown-menu .btn-default span {
  vertical-align: middle;
}

/*
 * The icons are not all the same width,
 * so we set a fix width for the icons
 * so that all dropdown options aligned with each others
 */
.livedata-dropdown-menu li a .fa {
  width: 1.4rem;
  text-align: center;
}

</style>
