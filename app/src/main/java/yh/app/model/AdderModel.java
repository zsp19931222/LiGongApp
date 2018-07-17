package yh.app.model;

import java.util.List;

public class AdderModel {

	private List<PBean> p;

	public List<PBean> getP() {
		return p;
	}

	public void setP(List<PBean> p) {
		this.p = p;
	}

	public static class PBean {
		/**
		 * n : 北京市
		 * v : 110000
		 * c : [{"n":"市辖区","v":"110100","d":[{"n":"东城区","v":"110101"},{"n":"西城区","v":"110102"},{"n":"崇文区","v":"110103"},{"n":"宣武区","v":"110104"},{"n":"朝阳区","v":"110105"},{"n":"丰台区","v":"110106"},{"n":"石景山区","v":"110107"},{"n":"海淀区","v":"110108"},{"n":"门头沟区","v":"110109"},{"n":"房山区","v":"110111"},{"n":"通州区","v":"110112"},{"n":"顺义区","v":"110113"},{"n":"昌平区","v":"110114"},{"n":"大兴区","v":"110115"},{"n":"怀柔区","v":"110116"},{"n":"平谷区","v":"110117"}]},{"n":"县","v":"110200","d":[{"n":"密云县","v":"110228"},{"n":"延庆县","v":"110229"}]}]
		 */

		private String n;
		private String v;
		private List<CBean> c;

		public String getN() {
			return n;
		}

		public void setN(String n) {
			this.n = n;
		}

		public String getV() {
			return v;
		}

		public void setV(String v) {
			this.v = v;
		}

		public List<CBean> getC() {
			return c;
		}

		public void setC(List<CBean> c) {
			this.c = c;
		}

		public static class CBean {
			/**
			 * n : 市辖区
			 * v : 110100
			 * d : [{"n":"东城区","v":"110101"},{"n":"西城区","v":"110102"},{"n":"崇文区","v":"110103"},{"n":"宣武区","v":"110104"},{"n":"朝阳区","v":"110105"},{"n":"丰台区","v":"110106"},{"n":"石景山区","v":"110107"},{"n":"海淀区","v":"110108"},{"n":"门头沟区","v":"110109"},{"n":"房山区","v":"110111"},{"n":"通州区","v":"110112"},{"n":"顺义区","v":"110113"},{"n":"昌平区","v":"110114"},{"n":"大兴区","v":"110115"},{"n":"怀柔区","v":"110116"},{"n":"平谷区","v":"110117"}]
			 */

			private String n;
			private String v;
			private List<DBean> d;

			public String getN() {
				return n;
			}

			public void setN(String n) {
				this.n = n;
			}

			public String getV() {
				return v;
			}

			public void setV(String v) {
				this.v = v;
			}

			public List<DBean> getD() {
				return d;
			}

			public void setD(List<DBean> d) {
				this.d = d;
			}

			public static class DBean {
				/**
				 * n : 东城区
				 * v : 110101
				 */

				private String n;
				private String v;

				public String getN() {
					return n;
				}

				public void setN(String n) {
					this.n = n;
				}

				public String getV() {
					return v;
				}

				public void setV(String v) {
					this.v = v;
				}
			}
		}
	}
}
