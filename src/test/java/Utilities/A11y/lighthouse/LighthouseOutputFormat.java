package Utilities.A11y.lighthouse;

public enum LighthouseOutputFormat {
		HTML("html"),
		JSON("json");

		private final String format;

		LighthouseOutputFormat(String format) {
			this.format = format;
		}

		public String getFormat() {
			return format;
		}
	}
