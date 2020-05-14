export default {
  props: {
    parentItem: {
      type: Object,
      default: () => {}
    },
    iconSize: Number
  },
  computed: {
    parentName() {
      return this.parentItem.name
    },
    children() {
      return this.parentItem.children
    },
    textColor() {
      return this.theme === 'dark' ? '#fff' : '#495060'
    }
  }
}
