package spinner;

public class FourGram {
	
	String fourgram;
	double logprob;
	double cumprob;
	
	public FourGram(String string, double log, double cum)
	{
		fourgram = string;
		logprob = log;
		cumprob = cum;
	}

	public String getFourgram() {
		return fourgram;
	}

	public void setFourgram(String fourgram) {
		this.fourgram = fourgram;
	}

	public double getLogprob() {
		return logprob;
	}

	public void setLogprob(double logprob) {
		this.logprob = logprob;
	}

	public double getCumprob() {
		return cumprob;
	}

	public void setCumprob(double cumprob) {
		this.cumprob = cumprob;
	}
	
}
