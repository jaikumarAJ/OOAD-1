package goru.assign2;

public enum MealType {
	VEGAN (1), LOW_CARB (2), HIGH_CARB(3);
	private int mealType;
	
	private MealType(int mealType) {
		this.mealType = mealType;
	}
}
