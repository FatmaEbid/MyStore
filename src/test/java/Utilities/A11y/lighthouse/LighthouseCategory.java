package Utilities.A11y.lighthouse;

public enum LighthouseCategory {
		ACCESSIBILITY("accessibility"),
		PERFORMANCE("performance"),
		SEO("seo"),
		BEST_PRACTICES("best-practices"),
		PWA("pwa");

		private final String value;

		LighthouseCategory(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
