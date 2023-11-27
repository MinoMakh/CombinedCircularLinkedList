package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {

	private CLinkedList<Year<Integer>> yearsList = new CLinkedList<>();
	private Button fileButton, statisticsButton, managementButton, newRecordButton, updateRecordButton,
			deleteRecordButton, searchRecordButton, backButton, dayStatisticsButton, monthStatisticsButton,
			yearStatisticsButton, generalStatisticsButton, saveFileButton;
	private Text fileText, newRecordText2, updateRecordText2, deleteRecordText2, searchRecordText2, dayStatisticsText3,
			monthStatisticsText3, yearStatisticsText3, generalStatisticsText3, saveFileText;
	private TextField israeliLinesField, gazaPowerField, egyptianLinesField, overallDemandField, powerCutsField,
			totalSupplyField, temperatureField, israeliLinesField2, gazaPowerField2, egyptianLinesField2,
			overallDemandField2, powerCutsField2, totalSupplyField2, temperatureField2;
	private DatePicker datePicker, datePicker2, datePicker3, datePicker4, datePicker5, datePicker6, datePicker7;
	private CheckBox israeliLinesCheck, gazaPowerCheck, egyptianLinesCheck, overallDemandCheck, powerCutsCheck,
			totalSupplyCheck, temperatureCheck;
	private RadioButton israeliLinesCheck2, gazaPowerCheck2, egyptianLinesCheck2, overallDemandCheck2, powerCutsCheck2,
			totalSupplyCheck2, temperatureCheck2, israeliLinesCheck3, gazaPowerCheck3, egyptianLinesCheck3,
			overallDemandCheck3, powerCutsCheck3, totalSupplyCheck3, temperatureCheck3, israeliLinesCheck4,
			gazaPowerCheck4, egyptianLinesCheck4, overallDemandCheck4, powerCutsCheck4, totalSupplyCheck4,
			temperatureCheck4, israeliLinesCheck5, gazaPowerCheck5, egyptianLinesCheck5, overallDemandCheck5,
			powerCutsCheck5, totalSupplyCheck5, temperatureCheck5;

	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(getMainMenu(), 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setFullScreen(true);
			primaryStage.setScene(scene);
			primaryStage.show();

			// START Event Handlers

			// Loading the file into the data structure
			fileButton.setOnAction(event -> {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select the file");
				fileChooser.setInitialDirectory(new File("C:/"));
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
				if (selectedFile != null) {
					readFile(selectedFile);
					statisticsButton.setDisable(false);
					managementButton.setDisable(false);
					saveFileButton.setDisable(false);
					fileText.setText("File: " + selectedFile.getName() + " has been selected.");
					fileText.setFill(Color.BLACK);
				}
			});

			// Saving the data into a file
			saveFileButton.setOnAction(event -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select the file");
				fileChooser.setInitialDirectory(new File("C:/"));
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
				if (selectedFile != null) {
					saveFile(selectedFile);
					saveFileText.setText("File: " + selectedFile.getName() + " has been saved.");
				}
			});

			// Opening the management system
			managementButton.setOnAction(event -> {
				Scene newScene = new Scene(getManagementUI(), 800, 800);
				primaryStage.setScene(newScene);
				primaryStage.setFullScreen(true);
				primaryStage.show();

				// Go back to main menu
				backButton.setOnAction(e -> {

					primaryStage.setScene(scene);
					primaryStage.setFullScreen(true);
					primaryStage.show();
				});

				// Adding a new record
				newRecordButton.setOnAction(e -> {

					// Checking if the input is valid
					if (!validInput(israeliLinesField, gazaPowerField, egyptianLinesField, overallDemandField,
							powerCutsField, totalSupplyField, temperatureField)) {
						newRecordText2.setText("Wrong input.");
						newRecordText2.setFill(Color.RED);
						return;
					}

					// Checking if a date is selected
					if (datePicker.getValue() == null) {
						newRecordText2.setText("Select a date.");
						newRecordText2.setFill(Color.RED);
						return;
					}
					// Getting the information from the data fields and creating their respective
					// objects
					String d[] = datePicker.getValue().toString().split("-");

					Month month = new Month(d[1]);
					Year year = new Year(d[0]);
					Date date = new Date(d[2], d[1], d[0]);
					Day newDay = new Day(date, israeliLinesField.getText(), gazaPowerField.getText(),
							egyptianLinesField.getText(), overallDemandField.getText(), powerCutsField.getText(),
							totalSupplyField.getText(), temperatureField.getText());
					// Inserting the year in case its not available
					if (searchYear(year) == null)
						yearsList.insertSorted(year);

					// Inserting the month in case its not available
					if (searchMonth(month, year) == null)
						((Year<Integer>) yearsList.search(year).getData()).getMonthList().insertSorted(month);

					// Inserting the day in case its not available and setting all the data
					if (searchDay(newDay, month, year) == null) {
						((Month<Integer>) ((Year<Integer>) yearsList.search(year).getData()).getMonthList()
								.search(month).getData()).getDayList().insertSorted(newDay);
						newRecordText2.setText("Record added.");
						newRecordText2.setFill(Color.BLACK);
					} else { // In case the day is already available in the data structure
						newRecordText2.setText("Record already available.");
						newRecordText2.setFill(Color.RED);
					}

				});

				// CheckBox Event Handler START

				// CheckBoxes for the update method to let the user choose the data to change
				israeliLinesCheck.setOnAction(e -> {
					if (israeliLinesCheck.isSelected())
						israeliLinesField2.setVisible(true);
					else
						israeliLinesField2.setVisible(false);
				});

				gazaPowerCheck.setOnAction(e -> {
					if (gazaPowerCheck.isSelected())
						gazaPowerField2.setVisible(true);
					else
						gazaPowerField2.setVisible(false);
				});

				egyptianLinesCheck.setOnAction(e -> {
					if (egyptianLinesCheck.isSelected())
						egyptianLinesField2.setVisible(true);
					else
						egyptianLinesField2.setVisible(false);
				});

				overallDemandCheck.setOnAction(e -> {
					if (overallDemandCheck.isSelected())
						overallDemandField2.setVisible(true);
					else
						overallDemandField2.setVisible(false);
				});

				powerCutsCheck.setOnAction(e -> {
					if (powerCutsCheck.isSelected())
						powerCutsField2.setVisible(true);
					else
						powerCutsField2.setVisible(false);
				});

				totalSupplyCheck.setOnAction(e -> {
					if (totalSupplyCheck.isSelected())
						totalSupplyField2.setVisible(true);
					else
						totalSupplyField2.setVisible(false);
				});

				temperatureCheck.setOnAction(e -> {
					if (temperatureCheck.isSelected())
						temperatureField2.setVisible(true);
					else
						temperatureField2.setVisible(false);
				});

				// CheckBox Event Handler END

				// Update a record
				updateRecordButton.setOnAction(e -> {

					// Checking if a date is selected
					if (datePicker2.getValue() == null) {
						updateRecordText2.setText("Select a date.");
						updateRecordText2.setFill(Color.RED);
						return;
					}

					// Getting the date from the user
					String d[] = datePicker2.getValue().toString().split("-");
					Date date = new Date(d[2], d[1], d[0]);
					Day day = new Day(date, 0, 0, 0, 0, 0, 0, 0);
					Month month = new Month(d[1]);
					Year year = new Year(d[0]);

					// Checking if the year record is available
					if (searchYear(year) == null) {
						updateRecordText2.setText("Record is not available.");
						updateRecordText2.setFill(Color.RED);
						return;
					}
					// Checking if the month record is available
					if (searchMonth(month, year) == null) {
						updateRecordText2.setText("Record is not available.");
						updateRecordText2.setFill(Color.RED);
						return;
					}
					// Checking if the day record is available
					if (searchDay(day, month, year) == null) {
						updateRecordText2.setText("Record is not available.");
						updateRecordText2.setFill(Color.RED);
						return;
					}
					// Getting the record selected from the user
					Day newDay = searchDay(day, month, year);

					// Checking if the data from the checkboxes the user selected is valid and
					// modifying it in case its valid
					if (israeliLinesCheck.isSelected()) {
						if (!validInput(israeliLinesField2)) {
							updateRecordText2.setText("Invalid input.");
							updateRecordText2.setFill(Color.RED);
							return;
						}
						newDay.setIsraeliLines(israeliLinesField2.getText());
					}

					if (gazaPowerCheck.isSelected()) {
						if (!validInput(gazaPowerField2)) {
							updateRecordText2.setText("Invalid input.");
							updateRecordText2.setFill(Color.RED);
							return;
						}
						newDay.setGazaPowerPlant(gazaPowerField2.getText());
					}

					if (egyptianLinesCheck.isSelected()) {
						if (!validInput(egyptianLinesField2)) {
							updateRecordText2.setText("Invalid input.");
							updateRecordText2.setFill(Color.RED);
							return;
						}
						newDay.setEgyptiansLines(egyptianLinesField2.getText());
					}

					if (overallDemandCheck.isSelected()) {
						if (!validInput(overallDemandField2)) {
							updateRecordText2.setText("Invalid input.");
							updateRecordText2.setFill(Color.RED);
							return;
						}
						newDay.setOverallDemand(overallDemandField2.getText());
					}

					if (powerCutsCheck.isSelected()) {
						if (!validInput(powerCutsField2)) {
							updateRecordText2.setText("Invalid input.");
							updateRecordText2.setFill(Color.RED);
							return;
						}
						newDay.setPowerCutsHoursDay(powerCutsField2.getText());
					}

					if (totalSupplyCheck.isSelected()) {
						if (!validInput(totalSupplyField2)) {
							updateRecordText2.setText("Invalid input.");
							updateRecordText2.setFill(Color.RED);
							return;
						}
						newDay.setTotalDailySupply(totalSupplyField2.getText());
					}

					if (temperatureCheck.isSelected()) {
						if (!validInput(temperatureField2)) {
							updateRecordText2.setText("Invalid input.");
							updateRecordText2.setFill(Color.RED);
							return;
						}
						newDay.setTemp(temperatureField2.getText());
					}

					updateRecordText2.setText("Record Updated.");
					updateRecordText2.setFill(Color.BLACK);
				});

				// Delete a record
				deleteRecordButton.setOnAction(e -> {

					// Checking if a date is selected
					if (datePicker3.getValue() == null) {
						deleteRecordText2.setText("Select a date.");
						deleteRecordText2.setFill(Color.RED);
						return;
					}

					// Getting the date from the user
					String d[] = datePicker3.getValue().toString().split("-");
					Date date = new Date(d[2], d[1], d[0]);
					Day day = new Day(date, 0, 0, 0, 0, 0, 0, 0);
					Month month = new Month(d[1]);
					Year year = new Year(d[0]);

					// Checking if the year record is available
					if (searchYear(year) == null) {
						deleteRecordText2.setText("Record is not avaible.");
						deleteRecordText2.setFill(Color.RED);
						return;
					}
					// Checking if the month record is available
					if (searchMonth(month, year) == null) {
						deleteRecordText2.setText("Record is not avaible.");
						deleteRecordText2.setFill(Color.RED);
						return;
					}
					// Checking if the day record is available
					if (searchDay(day, month, year) == null) {
						deleteRecordText2.setText("Record is not avaible.");
						deleteRecordText2.setFill(Color.RED);
						return;
					}
					// Deleting the record
					searchMonth(month, year).getDayList().delete(day);
					deleteRecordText2.setText("Record deleted.");
					deleteRecordText2.setFill(Color.BLACK);
				});

				// Searching for a record
				searchRecordButton.setOnAction(e -> {

					// Checking if a date is selected
					if (datePicker4.getValue() == null) {
						searchRecordText2.setText("Select a date.");
						searchRecordText2.setFill(Color.RED);
						return;
					}

					// Getting the date from the user
					String d[] = datePicker4.getValue().toString().split("-");
					Date date = new Date(d[2], d[1], d[0]);
					Day day = new Day(date, 0, 0, 0, 0, 0, 0, 0);
					Month month = new Month(d[1]);
					Year year = new Year(d[0]);

					// Checking if the year is available
					if (searchYear(year) == null) {
						searchRecordText2.setText("Record is not avaible.");
						searchRecordText2.setFill(Color.RED);
						return;
					}
					// Checking if the month is available
					if (searchMonth(month, year) == null) {
						searchRecordText2.setText("Record is not avaible.");
						searchRecordText2.setFill(Color.RED);
						return;
					}
					// Checking if the day is available
					if (searchDay(day, month, year) == null) {
						searchRecordText2.setText("Record is not avaible.");
						searchRecordText2.setFill(Color.RED);
						return;
					}
					searchRecordText2.setText(searchDay(day, month, year).toString());
					searchRecordText2.setFill(Color.BLACK);

				});
			});

			// Opening the statistics system
			statisticsButton.setOnAction(event -> {
				Scene newScene = new Scene(getStatisticsUI(), 800, 800);
				primaryStage.setScene(newScene);
				primaryStage.setFullScreen(true);
				primaryStage.show();

				// Back to main menu button
				backButton.setOnAction(e -> {

					primaryStage.setScene(scene);
					primaryStage.setFullScreen(true);
					primaryStage.show();

				});

				// Statistics for a specific day
				dayStatisticsButton.setOnAction(e -> {

					// Checking if a date is selected
					if (datePicker5.getValue() == null) {
						dayStatisticsText3.setText("Select a date.");
						dayStatisticsText3.setFill(Color.RED);
						return;
					}

					// Checking if one checkbox is selected
					if (!israeliLinesCheck2.isSelected() && !gazaPowerCheck2.isSelected()
							&& !egyptianLinesCheck2.isSelected() && !overallDemandCheck2.isSelected()
							&& !powerCutsCheck2.isSelected() && !totalSupplyCheck2.isSelected()
							&& !temperatureCheck2.isSelected()) {
						dayStatisticsText3.setText("Select a checkbox.");
						dayStatisticsText3.setFill(Color.RED);
						return;
					}

					// Getting the day from the user
					String d[] = datePicker5.getValue().toString().split("-");
					Date date = new Date(d[2], d[1], d[0]);
					Day day = new Day(date, 0, 0, 0, 0, 0, 0, 0);
					Month month = new Month(d[1]);
					Year year = new Year(d[0]);

					// Setting up all the statistics to calculate (maximum and minimum initially at
					// head)
					double total = 0;
					int numberDays = 0;
					Day maximum = (Day) yearsList.getHead().getData().getMonthList().getHead().getData().getDayList()
							.search(day).getData();
					Day minimum = (Day) yearsList.getHead().getData().getMonthList().getHead().getData().getDayList()
							.search(day).getData();

					// Looping trough the year list
					Node currentYear = yearsList.getHead();
					do {

						// Looping trough the month list
						Node currentMonth = ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData())
								.getData()).getMonthList().getHead();
						do {

							// Looping trough the day list
							Node currentDay = ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData())
									.getData()).getMonthList().search((Month<Integer>) currentMonth.getData()).getData()
									.getDayList().getHead();
							do {

								// If the current day equals the day we want
								if (currentDay.getData().compareTo(day) == 0) {

									/*
									 * Checking the checkbox the user selected and checking for the total, maximums
									 * and minimums in the data
									 */
									if (israeliLinesCheck2.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getIsraeliLines());
										numberDays++;
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getIsraeliLines()) >= Double
														.parseDouble((String) maximum.getIsraeliLines()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getIsraeliLines()) <= Double
														.parseDouble((String) minimum.getIsraeliLines()))
											minimum = (Day) currentDay.getData();
									}

									if (gazaPowerCheck2.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getGazaPowerPlant());
										numberDays++;
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getGazaPowerPlant()) >= Double
														.parseDouble((String) maximum.getGazaPowerPlant()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getGazaPowerPlant()) <= Double
														.parseDouble((String) minimum.getGazaPowerPlant()))
											minimum = (Day) currentDay.getData();
									}

									if (egyptianLinesCheck2.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getEgyptiansLines());
										numberDays++;
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getEgyptiansLines()) >= Double
														.parseDouble((String) maximum.getEgyptiansLines()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getEgyptiansLines()) <= Double
														.parseDouble((String) minimum.getEgyptiansLines()))
											minimum = (Day) currentDay.getData();
									}

									if (overallDemandCheck2.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getOverallDemand());
										numberDays++;
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getOverallDemand()) >= Double
														.parseDouble((String) maximum.getOverallDemand()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getOverallDemand()) <= Double
														.parseDouble((String) minimum.getOverallDemand()))
											minimum = (Day) currentDay.getData();
									}

									if (powerCutsCheck2.isSelected()) {
										total += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay());
										numberDays++;
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay()) >= Double
														.parseDouble((String) maximum.getPowerCutsHoursDay()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay()) <= Double
														.parseDouble((String) minimum.getPowerCutsHoursDay()))
											minimum = (Day) currentDay.getData();
									}

									if (totalSupplyCheck2.isSelected()) {
										total += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply());
										numberDays++;
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply()) >= Double
														.parseDouble((String) maximum.getTotalDailySupply()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply()) <= Double
														.parseDouble((String) minimum.getTotalDailySupply()))
											minimum = (Day) currentDay.getData();
									}

									if (temperatureCheck2.isSelected()) {
										total += Double.parseDouble((String) ((Day) currentDay.getData()).getTemp());
										numberDays++;
										if (Double
												.parseDouble((String) ((Day) currentDay.getData()).getTemp()) >= Double
														.parseDouble((String) maximum.getTemp()))
											maximum = (Day) currentDay.getData();

										if (Double
												.parseDouble((String) ((Day) currentDay.getData()).getTemp()) <= Double
														.parseDouble((String) minimum.getTemp()))
											minimum = (Day) currentDay.getData();
									}
									break; // Breaking the current loop since there's no need to check for the rest of
											// the days
								}
								currentDay = currentDay.getNext();

							} while (currentDay != ((Year<Integer>) yearsList
									.search((Year<Integer>) currentYear.getData()).getData()).getMonthList()
									.search((Month<Integer>) currentMonth.getData()).getData().getDayList().getHead());

							currentMonth = currentMonth.getNext();

						} while (currentMonth != ((Year<Integer>) yearsList
								.search((Year<Integer>) currentYear.getData()).getData()).getMonthList().getHead());

						currentYear = currentYear.getNext();

					} while (currentYear != yearsList.getHead());

					dayStatisticsText3.setText("The maximum day is: \n" + maximum.toString()
							+ "\nThe Minimum day is: \n" + minimum.toString() + "\nThe total is: " + total
							+ "\nThe average is: " + (total / numberDays));
					dayStatisticsText3.setFill(Color.BLACK);
				});

				// Statistics for a specific month
				monthStatisticsButton.setOnAction(e -> {

					// Checking if a date is selected
					if (datePicker6.getValue() == null) {
						monthStatisticsText3.setText("Select a date.");
						monthStatisticsText3.setFill(Color.RED);
						return;
					}

					// Checking if a checkbox is selected
					if (!israeliLinesCheck3.isSelected() && !gazaPowerCheck3.isSelected()
							&& !egyptianLinesCheck3.isSelected() && !overallDemandCheck3.isSelected()
							&& !powerCutsCheck3.isSelected() && !totalSupplyCheck3.isSelected()
							&& !temperatureCheck3.isSelected()) {
						monthStatisticsText3.setText("Select a checkbox.");
						monthStatisticsText3.setFill(Color.RED);
						return;
					}

					// Getting the month from the user
					String d[] = datePicker6.getValue().toString().split("-");
					Date date = new Date(d[2], d[1], d[0]);
					Day day = new Day(date, 0, 0, 0, 0, 0, 0, 0);
					Month month = new Month(d[1]);

					// Setting up all the statistics we want to calculate
					double total = 0;
					double maximum = 0;
					double minimum = 9999999;
					int numberMonths = 0;
					Node maximumMonth = yearsList.getHead().getData().getMonthList().search(month);
					Node minimumMonth = yearsList.getHead().getData().getMonthList().search(month);
					Node maximumYear = yearsList.getHead();
					Node minimumYear = yearsList.getHead();

					// Looping trough the years
					Node currentYear = yearsList.getHead();
					do {
						Year year = (Year) currentYear.getData();

						// Looping trough the months
						Node currentMonth = ((Year<Integer>) yearsList.search(year).getData()).getMonthList().getHead();
						do {
							Month month1 = (Month) currentMonth.getData();

							// If the current month equals the month we want
							if (month.compareTo((Month) currentMonth.getData()) == 0) {
								double totalMonth = 0;

								// Looping trough the days
								Node currentDay = ((Month) ((Year<Integer>) yearsList.search(year).getData())
										.getMonthList().search(month1).getData()).getDayList().getHead();
								do {

									// Collecting the desired data from the user's checkbox
									if (israeliLinesCheck3.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getIsraeliLines());
										totalMonth += Double
												.parseDouble((String) ((Day) currentDay.getData()).getIsraeliLines());
									}

									if (gazaPowerCheck3.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getGazaPowerPlant());
										totalMonth += Double
												.parseDouble((String) ((Day) currentDay.getData()).getGazaPowerPlant());
									}

									if (egyptianLinesCheck3.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getEgyptiansLines());
										totalMonth += Double
												.parseDouble((String) ((Day) currentDay.getData()).getEgyptiansLines());
									}

									if (overallDemandCheck3.isSelected()) {
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getOverallDemand());
										totalMonth += Double
												.parseDouble((String) ((Day) currentDay.getData()).getOverallDemand());
									}

									if (powerCutsCheck3.isSelected()) {
										total += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay());
										totalMonth += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay());
									}

									if (totalSupplyCheck3.isSelected()) {
										total += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply());
										totalMonth += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply());
									}

									if (temperatureCheck3.isSelected()) {
										total += Double.parseDouble((String) ((Day) currentDay.getData()).getTemp());
										totalMonth += Double
												.parseDouble((String) ((Day) currentDay.getData()).getTemp());
									}

									currentDay = currentDay.getNext();

								} while (currentDay != ((Month) ((Year<Integer>) yearsList.search(year).getData())
										.getMonthList().search(month1).getData()).getDayList().getHead());

								numberMonths++;

								// Checking for the maximum and minimum month data
								if (totalMonth > maximum) {
									maximum = totalMonth;
									maximumMonth = currentMonth;
									maximumYear = currentYear;
								}

								if (totalMonth < minimum) {
									minimum = totalMonth;
									minimumMonth = currentMonth;
									minimumYear = currentYear;
								}

								break; // Break the loop if the month is find since there is no need to check the rest
										// of the months
							}

							currentMonth = currentMonth.getNext();

						} while (currentMonth != yearsList.search((Year<Integer>) currentYear.getData()).getData()
								.getMonthList().getHead());

						currentYear = currentYear.getNext();

					} while (currentYear != yearsList.getHead());

					monthStatisticsText3.setText("The Maximum Month is: \n " + maximumYear.getData().toString() + "/"
							+ maximumMonth.getData().toString() + " : " + maximum + "\nThe Minimum Month is: \n"
							+ minimumYear.getData().toString() + "/" + minimumMonth.getData().toString() + " : "
							+ minimum + "\nThe total is: " + total + "\nThe average is: " + (total / numberMonths));
					monthStatisticsText3.setFill(Color.BLACK);
				});

				// Statistics for a specific year
				yearStatisticsButton.setOnAction(e -> {

					// Checking if a date is selected
					if (datePicker7.getValue() == null) {
						yearStatisticsText3.setText("Select a date.");
						yearStatisticsText3.setFill(Color.RED);
						return;
					}

					// Checking if a checkbox is selected
					if (!israeliLinesCheck4.isSelected() && !gazaPowerCheck4.isSelected()
							&& !egyptianLinesCheck4.isSelected() && !overallDemandCheck4.isSelected()
							&& !powerCutsCheck4.isSelected() && !totalSupplyCheck4.isSelected()
							&& !temperatureCheck4.isSelected()) {
						yearStatisticsText3.setText("Select a checkbox.");
						yearStatisticsText3.setFill(Color.RED);
						return;
					}

					// Getting the year from the user
					String d[] = datePicker7.getValue().toString().split("-");
					Year year = new Year(d[0]);

					// Checking if the year is available
					if (yearsList.search(year) == null) {
						yearStatisticsText3.setText("The year record is not available.");
						yearStatisticsText3.setFill(Color.RED);
						return;
					}

					// Setting up all the statistics we want to calculate
					double total = 0;
					int numberDays = 0;
					Day maximum = ((Year<Integer>) yearsList.search(year).getData()).getMonthList().getHead().getData()
							.getDayList().getHead().getData();
					Day minimum = ((Year<Integer>) yearsList.search(year).getData()).getMonthList().getHead().getData()
							.getDayList().getHead().getData();

					// Looping trough the years list
					Node currentYear = yearsList.getHead();
					do {

						// If the current year equals the year we want
						if (currentYear.getData().compareTo(year) == 0) {

							// Looping trough the months list
							Node currentMonth = ((Year<Integer>) yearsList.search(year).getData()).getMonthList()
									.getHead();
							do {

								// Looping trough the days list
								Node currentDay = ((Year<Integer>) yearsList.search(year).getData()).getMonthList()
										.search((Month<Integer>) currentMonth.getData()).getData().getDayList()
										.getHead();
								do {

									// Getting the data desired from the selected checkbox and getting the maximum
									// and minimum
									if (israeliLinesCheck4.isSelected()) {
										numberDays++;
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getIsraeliLines());
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getIsraeliLines()) >= Double
														.parseDouble((String) maximum.getIsraeliLines()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getIsraeliLines()) <= Double
														.parseDouble((String) minimum.getIsraeliLines()))
											minimum = (Day) currentDay.getData();
									}

									if (gazaPowerCheck4.isSelected()) {
										numberDays++;
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getGazaPowerPlant());
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getGazaPowerPlant()) >= Double
														.parseDouble((String) maximum.getGazaPowerPlant()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getGazaPowerPlant()) <= Double
														.parseDouble((String) minimum.getGazaPowerPlant()))
											minimum = (Day) currentDay.getData();
									}

									if (egyptianLinesCheck4.isSelected()) {
										numberDays++;
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getEgyptiansLines());
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getEgyptiansLines()) >= Double
														.parseDouble((String) maximum.getEgyptiansLines()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getEgyptiansLines()) <= Double
														.parseDouble((String) minimum.getEgyptiansLines()))
											minimum = (Day) currentDay.getData();
									}

									if (overallDemandCheck4.isSelected()) {
										numberDays++;
										total += Double
												.parseDouble((String) ((Day) currentDay.getData()).getOverallDemand());
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getOverallDemand()) >= Double
														.parseDouble((String) maximum.getOverallDemand()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getOverallDemand()) <= Double
														.parseDouble((String) minimum.getOverallDemand()))
											minimum = (Day) currentDay.getData();
									}

									if (powerCutsCheck4.isSelected()) {
										numberDays++;
										total += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay());
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay()) >= Double
														.parseDouble((String) maximum.getPowerCutsHoursDay()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getPowerCutsHoursDay()) <= Double
														.parseDouble((String) minimum.getPowerCutsHoursDay()))
											minimum = (Day) currentDay.getData();
									}

									if (totalSupplyCheck4.isSelected()) {
										numberDays++;
										total += Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply());
										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply()) >= Double
														.parseDouble((String) maximum.getTotalDailySupply()))
											maximum = (Day) currentDay.getData();

										if (Double.parseDouble(
												(String) ((Day) currentDay.getData()).getTotalDailySupply()) <= Double
														.parseDouble((String) minimum.getTotalDailySupply()))
											minimum = (Day) currentDay.getData();
									}

									if (temperatureCheck4.isSelected()) {
										numberDays++;
										total += Double.parseDouble((String) ((Day) currentDay.getData()).getTemp());
										if (Double
												.parseDouble((String) ((Day) currentDay.getData()).getTemp()) >= Double
														.parseDouble((String) maximum.getTemp()))
											maximum = (Day) currentDay.getData();

										if (Double
												.parseDouble((String) ((Day) currentDay.getData()).getTemp()) <= Double
														.parseDouble((String) minimum.getTemp()))
											minimum = (Day) currentDay.getData();
									}

									currentDay = currentDay.getNext();

								} while (currentDay != ((Year<Integer>) yearsList.search(year).getData()).getMonthList()
										.search((Month<Integer>) currentMonth.getData()).getData().getDayList()
										.getHead());

								currentMonth = currentMonth.getNext();

							} while (currentMonth != ((Year<Integer>) yearsList.search(year).getData()).getMonthList()
									.getHead());

							break; // Break the loop since theres no need to check the rest of the years
						}
						currentYear = currentYear.getNext();

					} while (currentYear != yearsList.getHead());

					yearStatisticsText3.setText(
							"The maximum is: \n" + maximum.toString() + "\nThe Minimum is: \n" + minimum.toString()
									+ "\nThe total is: " + total + "\nThe average is: " + (total / numberDays));
					yearStatisticsText3.setFill(Color.BLACK);
				});

				// Statistics for all the days on all months and all years
				generalStatisticsButton.setOnAction(e -> {

					// Checking if a checkbox is selected
					if (!israeliLinesCheck5.isSelected() && !gazaPowerCheck5.isSelected()
							&& !egyptianLinesCheck5.isSelected() && !overallDemandCheck5.isSelected()
							&& !powerCutsCheck5.isSelected() && !totalSupplyCheck5.isSelected()
							&& !temperatureCheck5.isSelected()) {
						generalStatisticsText3.setText("Select a checkbox.");
						generalStatisticsText3.setFill(Color.RED);
						return;
					}

					// Setting up all the statistics we want to calculate
					double total = 0;
					int numberDays = 0;

					Day maximum = yearsList.getHead().getData().getMonthList().getHead().getData().getDayList()
							.getHead().getData();
					Day minimum = yearsList.getHead().getData().getMonthList().getHead().getData().getDayList()
							.getHead().getData();

					// Looping trough the years list
					Node currentYear = yearsList.getHead();
					do {

						// Looping trough the months list
						Node currentMonth = ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData())
								.getData()).getMonthList().getHead();
						do {

							// Looping trough the days list
							Node currentDay = ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData())
									.getData()).getMonthList().search((Month<Integer>) currentMonth.getData()).getData()
									.getDayList().getHead();

							do {

								// Getting the data desired from the selected checkbox and getting the maximum
								// and minimum
								if (israeliLinesCheck5.isSelected()) {
									total += Double
											.parseDouble((String) ((Day) currentDay.getData()).getIsraeliLines());
									numberDays++;
									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getIsraeliLines()) >= Double
													.parseDouble((String) maximum.getIsraeliLines()))
										maximum = (Day) currentDay.getData();

									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getIsraeliLines()) <= Double
													.parseDouble((String) minimum.getIsraeliLines()))
										minimum = (Day) currentDay.getData();
								}

								if (gazaPowerCheck5.isSelected()) {
									total += Double
											.parseDouble((String) ((Day) currentDay.getData()).getGazaPowerPlant());
									numberDays++;
									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getGazaPowerPlant()) >= Double
													.parseDouble((String) maximum.getGazaPowerPlant()))
										maximum = (Day) currentDay.getData();

									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getGazaPowerPlant()) <= Double
													.parseDouble((String) minimum.getGazaPowerPlant()))
										minimum = (Day) currentDay.getData();
								}

								if (egyptianLinesCheck5.isSelected()) {
									total += Double
											.parseDouble((String) ((Day) currentDay.getData()).getEgyptiansLines());
									numberDays++;
									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getEgyptiansLines()) >= Double
													.parseDouble((String) maximum.getEgyptiansLines()))
										maximum = (Day) currentDay.getData();

									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getEgyptiansLines()) <= Double
													.parseDouble((String) minimum.getEgyptiansLines()))
										minimum = (Day) currentDay.getData();
								}

								if (overallDemandCheck5.isSelected()) {
									total += Double
											.parseDouble((String) ((Day) currentDay.getData()).getOverallDemand());
									numberDays++;
									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getOverallDemand()) >= Double
													.parseDouble((String) maximum.getOverallDemand()))
										maximum = (Day) currentDay.getData();

									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getOverallDemand()) <= Double
													.parseDouble((String) minimum.getOverallDemand()))
										minimum = (Day) currentDay.getData();
								}

								if (powerCutsCheck5.isSelected()) {
									total += Double
											.parseDouble((String) ((Day) currentDay.getData()).getPowerCutsHoursDay());
									numberDays++;
									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getPowerCutsHoursDay()) >= Double
													.parseDouble((String) maximum.getPowerCutsHoursDay()))
										maximum = (Day) currentDay.getData();

									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getPowerCutsHoursDay()) <= Double
													.parseDouble((String) minimum.getPowerCutsHoursDay()))
										minimum = (Day) currentDay.getData();
								}

								if (totalSupplyCheck5.isSelected()) {
									total += Double
											.parseDouble((String) ((Day) currentDay.getData()).getTotalDailySupply());
									numberDays++;
									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getTotalDailySupply()) >= Double
													.parseDouble((String) maximum.getTotalDailySupply()))
										maximum = (Day) currentDay.getData();

									if (Double.parseDouble(
											(String) ((Day) currentDay.getData()).getTotalDailySupply()) <= Double
													.parseDouble((String) minimum.getTotalDailySupply()))
										minimum = (Day) currentDay.getData();
								}

								if (temperatureCheck5.isSelected()) {
									total += Double.parseDouble((String) ((Day) currentDay.getData()).getTemp());
									numberDays++;
									if (Double.parseDouble((String) ((Day) currentDay.getData()).getTemp()) >= Double
											.parseDouble((String) maximum.getTemp()))
										maximum = (Day) currentDay.getData();

									if (Double.parseDouble((String) ((Day) currentDay.getData()).getTemp()) <= Double
											.parseDouble((String) minimum.getTemp()))
										minimum = (Day) currentDay.getData();
								}

								currentDay = currentDay.getNext();

							} while (currentDay != ((Year<Integer>) yearsList
									.search((Year<Integer>) currentYear.getData()).getData()).getMonthList()
									.search((Month<Integer>) currentMonth.getData()).getData().getDayList().getHead());

							currentMonth = currentMonth.getNext();

						} while (currentMonth != ((Year<Integer>) yearsList
								.search((Year<Integer>) currentYear.getData()).getData()).getMonthList().getHead());

						currentYear = currentYear.getNext();

					} while (currentYear != yearsList.getHead());

					generalStatisticsText3.setText(
							"The maximum is: \n" + maximum.toString() + "\nThe Minimum is: \n" + minimum.toString()
									+ "\nThe total is: " + total + "\nThe average is: " + (total / numberDays));
					generalStatisticsText3.setFill(Color.BLACK);
				});
			});

			// END Event Handlers
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	// Getting the main menu graphical user interface
	private HBox getMainMenu() {
		HBox mainMenuHBox = new HBox(100);

		// Management system button
		managementButton = new Button("Manegement");
		managementButton.setScaleX(2);
		managementButton.setScaleY(2);
		managementButton.setDisable(true);
		VBox managementVBox = new VBox();
		managementVBox.getChildren().add(managementButton);
		managementVBox.setAlignment(Pos.CENTER);

		// File loader button
		fileButton = new Button("Choose File");
		fileButton.setScaleX(2);
		fileButton.setScaleY(2);
		fileText = new Text("");
		fileText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		VBox fileVBox = new VBox(50);
		fileVBox.setAlignment(Pos.CENTER);
		fileVBox.getChildren().addAll(fileButton, fileText);

		// File saver button
		saveFileButton = new Button("Save to File");
		saveFileButton.setScaleX(2);
		saveFileButton.setScaleY(2);
		saveFileButton.setDisable(true);
		saveFileText = new Text("");
		saveFileText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		VBox saveFileVBox = new VBox(50);
		saveFileVBox.getChildren().addAll(saveFileButton, saveFileText);
		saveFileVBox.setAlignment(Pos.CENTER);

		// Statistics system button
		statisticsButton = new Button("Statitics");
		statisticsButton.setScaleX(2);
		statisticsButton.setScaleY(2);
		statisticsButton.setDisable(true);
		VBox statisticsVBox = new VBox();
		statisticsVBox.getChildren().add(statisticsButton);
		statisticsVBox.setAlignment(Pos.CENTER);

		mainMenuHBox.getChildren().addAll(managementVBox, fileVBox, saveFileVBox, statisticsVBox);
		mainMenuHBox.setAlignment(Pos.CENTER);

		return mainMenuHBox;
	}

	// Getting the management graphical user interface
	private HBox getManagementUI() {
		HBox managementHBox = new HBox(100);

		// Back to main menu
		VBox backButtonVBox = new VBox(20);
		Text backButtonText = new Text("Back to Main Menu");
		backButtonText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		backButton = new Button("Back");
		backButton.setScaleX(2);
		backButton.setScaleY(2);
		backButtonVBox.getChildren().addAll(backButtonText, backButton);
		backButtonVBox.setAlignment(Pos.CENTER);

		// Add record
		VBox newRecordVBox = new VBox(20);
		Text newRecordText = new Text("New Record:");
		newRecordText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		// Record fields
		israeliLinesField = new TextField("Enter the Israeli Lines");
		gazaPowerField = new TextField("Enter Gaza Power Plant");
		egyptianLinesField = new TextField("Enter the Egyptian Lines");
		overallDemandField = new TextField("Enter the Overall Demand");
		powerCutsField = new TextField("Enter the Hours Power Cut");
		totalSupplyField = new TextField("Enter the total Supply");
		temperatureField = new TextField("Enter the Temperature");
		datePicker = new DatePicker();

		// Add record button
		newRecordButton = new Button("Add");
		newRecordButton.setScaleX(2);
		newRecordButton.setScaleY(2);
		newRecordText2 = new Text("");
		newRecordText2.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		newRecordVBox.getChildren().addAll(newRecordText, israeliLinesField, gazaPowerField, egyptianLinesField,
				overallDemandField, powerCutsField, totalSupplyField, temperatureField, datePicker, newRecordButton,
				newRecordText2);
		newRecordVBox.setAlignment(Pos.CENTER);

		// Update Record
		VBox updateRecordVBox = new VBox(20);
		Text updateRecordText = new Text("Update Record:");
		updateRecordText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		// Update fields and checkboxes
		israeliLinesCheck = new CheckBox("Israeli Lines");
		israeliLinesField2 = new TextField("Enter the Israeli Lines");
		israeliLinesField2.setVisible(false);

		gazaPowerCheck = new CheckBox("Gaza Power Plant");
		gazaPowerField2 = new TextField("Enter Gaza Power Plant");
		gazaPowerField2.setVisible(false);

		egyptianLinesCheck = new CheckBox("Egyptian Lines");
		egyptianLinesField2 = new TextField("Enter the Egyptian Lines");
		egyptianLinesField2.setVisible(false);

		overallDemandCheck = new CheckBox("Overall Demand");
		overallDemandField2 = new TextField("Enter the Overall Demand");
		overallDemandField2.setVisible(false);

		powerCutsCheck = new CheckBox("Power Hour Cuts");
		powerCutsField2 = new TextField("Enter the Hours Power Cut");
		powerCutsField2.setVisible(false);

		totalSupplyCheck = new CheckBox("Total Supply");
		totalSupplyField2 = new TextField("Enter the total Supply");
		totalSupplyField2.setVisible(false);

		temperatureCheck = new CheckBox("Temperature");
		temperatureField2 = new TextField("Enter the Temperature");
		temperatureField2.setVisible(false);

		datePicker2 = new DatePicker();
		updateRecordButton = new Button("Update");
		updateRecordButton.setScaleX(2);
		updateRecordButton.setScaleY(2);

		updateRecordText2 = new Text("");
		updateRecordText2.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		updateRecordVBox.getChildren().addAll(updateRecordText, israeliLinesCheck, israeliLinesField2, gazaPowerCheck,
				gazaPowerField2, egyptianLinesCheck, egyptianLinesField2, overallDemandCheck, overallDemandField2,
				powerCutsCheck, powerCutsField2, totalSupplyCheck, totalSupplyField2, temperatureCheck,
				temperatureField2, datePicker2, updateRecordButton, updateRecordText2);
		updateRecordVBox.setAlignment(Pos.CENTER);

		// Delete record
		VBox deleteRecordVBox = new VBox(20);
		Text deleteRecordText = new Text("Delete Record:");
		deleteRecordText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		// Delete record button
		datePicker3 = new DatePicker();
		deleteRecordButton = new Button("Delete");
		deleteRecordButton.setScaleX(2);
		deleteRecordButton.setScaleY(2);

		deleteRecordText2 = new Text("");
		deleteRecordText2.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		deleteRecordVBox.getChildren().addAll(deleteRecordText, datePicker3, deleteRecordButton, deleteRecordText2);
		deleteRecordVBox.setAlignment(Pos.CENTER);

		// Search record
		VBox searchRecordVBox = new VBox(20);
		Text searchRecordText = new Text("Search Record:");
		searchRecordText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		// Search record button
		datePicker4 = new DatePicker();
		searchRecordButton = new Button("Search");
		searchRecordButton.setScaleX(2);
		searchRecordButton.setScaleY(2);

		searchRecordText2 = new Text("");
		searchRecordText2.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		searchRecordVBox.getChildren().addAll(searchRecordText, datePicker4, searchRecordButton, searchRecordText2);
		searchRecordVBox.setAlignment(Pos.CENTER);

		managementHBox.getChildren().addAll(backButtonVBox, newRecordVBox, updateRecordVBox, deleteRecordVBox,
				searchRecordVBox);
		managementHBox.setAlignment(Pos.CENTER);
		return managementHBox;
	}

	// Getting the statistics graphical user interface
	private HBox getStatisticsUI() {
		HBox statisticsHBox = new HBox(100);

		// Back to main menu button
		VBox backButtonVBox = new VBox(20);
		Text backButtonText = new Text("Back to Main Menu");
		backButtonText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));
		backButton = new Button("Back");
		backButton.setScaleX(2);
		backButton.setScaleY(2);
		backButtonVBox.getChildren().addAll(backButtonText, backButton);
		backButtonVBox.setAlignment(Pos.CENTER);

		// Specific day statistics
		VBox dayStatisticsVBox = new VBox(20);
		Text dayStatisticsText = new Text("Search for Specific Days:");
		dayStatisticsText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		datePicker5 = new DatePicker();
		Text dayStatisticsText2 = new Text("Select the Data you Desire:");

		// RadioButtons for the user desired data
		ToggleGroup group = new ToggleGroup();
		israeliLinesCheck2 = new RadioButton("Israeli Lines");
		israeliLinesCheck2.setToggleGroup(group);
		gazaPowerCheck2 = new RadioButton("Gaza Power Plant");
		gazaPowerCheck2.setToggleGroup(group);
		egyptianLinesCheck2 = new RadioButton("Egyptian Lines");
		egyptianLinesCheck2.setToggleGroup(group);
		overallDemandCheck2 = new RadioButton("Overall Demand");
		overallDemandCheck2.setToggleGroup(group);
		powerCutsCheck2 = new RadioButton("Power Hour Cuts");
		powerCutsCheck2.setToggleGroup(group);
		totalSupplyCheck2 = new RadioButton("Total Supply");
		totalSupplyCheck2.setToggleGroup(group);
		temperatureCheck2 = new RadioButton("Temperature");
		temperatureCheck2.setToggleGroup(group);

		dayStatisticsButton = new Button("Search");
		dayStatisticsButton.setScaleX(2);
		dayStatisticsButton.setScaleY(2);

		dayStatisticsText3 = new Text("");
		dayStatisticsText3.setFont(Font.font("Gill Sans", FontWeight.BOLD, 14));
		dayStatisticsVBox.getChildren().addAll(dayStatisticsText, datePicker5, dayStatisticsText2, israeliLinesCheck2,
				egyptianLinesCheck2, overallDemandCheck2, powerCutsCheck2, totalSupplyCheck2, temperatureCheck2,
				dayStatisticsButton, dayStatisticsText3);
		dayStatisticsVBox.setAlignment(Pos.CENTER);

		// Specific month statistics
		VBox monthStatisticsVBox = new VBox(20);
		Text monthStatisticsText = new Text("Search for Specific Months:");
		monthStatisticsText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		datePicker6 = new DatePicker();
		Text monthStatisticsText2 = new Text("Select the Data you Desire:");

		// RadioButtons for the user desired data
		ToggleGroup group2 = new ToggleGroup();
		israeliLinesCheck3 = new RadioButton("Israeli Lines");
		israeliLinesCheck3.setToggleGroup(group2);
		gazaPowerCheck3 = new RadioButton("Gaza Power Plant");
		gazaPowerCheck3.setToggleGroup(group2);
		egyptianLinesCheck3 = new RadioButton("Egyptian Lines");
		egyptianLinesCheck3.setToggleGroup(group2);
		overallDemandCheck3 = new RadioButton("Overall Demand");
		overallDemandCheck3.setToggleGroup(group2);
		powerCutsCheck3 = new RadioButton("Power Hour Cuts");
		powerCutsCheck3.setToggleGroup(group2);
		totalSupplyCheck3 = new RadioButton("Total Supply");
		totalSupplyCheck3.setToggleGroup(group2);
		temperatureCheck3 = new RadioButton("Temperature");
		temperatureCheck3.setToggleGroup(group2);

		monthStatisticsButton = new Button("Search");
		monthStatisticsButton.setScaleX(2);
		monthStatisticsButton.setScaleY(2);

		monthStatisticsText3 = new Text("");
		monthStatisticsText3.setFont(Font.font("Gill Sans", FontWeight.BOLD, 14));
		monthStatisticsVBox.getChildren().addAll(monthStatisticsText, datePicker6, monthStatisticsText2,
				israeliLinesCheck3, gazaPowerCheck3, egyptianLinesCheck3, overallDemandCheck3, totalSupplyCheck3,
				temperatureCheck3, monthStatisticsButton, monthStatisticsText3);
		monthStatisticsVBox.setAlignment(Pos.CENTER);

		// Specific year statistics
		VBox yearStatisticsVBox = new VBox(20);
		Text yearStatisticsText = new Text("Search for Specific Year:");
		yearStatisticsText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		datePicker7 = new DatePicker();
		Text yearStatisticsText2 = new Text("Select the Data you Desire:");

		// RadioButtons for the user desired data
		ToggleGroup group3 = new ToggleGroup();
		israeliLinesCheck4 = new RadioButton("Israeli Lines");
		israeliLinesCheck4.setToggleGroup(group2);
		gazaPowerCheck4 = new RadioButton("Gaza Power Plant");
		gazaPowerCheck4.setToggleGroup(group2);
		egyptianLinesCheck4 = new RadioButton("Egyptian Lines");
		egyptianLinesCheck4.setToggleGroup(group2);
		overallDemandCheck4 = new RadioButton("Overall Demand");
		overallDemandCheck4.setToggleGroup(group2);
		powerCutsCheck4 = new RadioButton("Power Hour Cuts");
		powerCutsCheck4.setToggleGroup(group2);
		totalSupplyCheck4 = new RadioButton("Total Supply");
		totalSupplyCheck4.setToggleGroup(group2);
		temperatureCheck4 = new RadioButton("Temperature");
		temperatureCheck4.setToggleGroup(group2);

		yearStatisticsButton = new Button("Search");
		yearStatisticsButton.setScaleX(2);
		yearStatisticsButton.setScaleY(2);

		yearStatisticsText3 = new Text("");
		yearStatisticsText3.setFont(Font.font("Gill Sans", FontWeight.BOLD, 14));
		yearStatisticsVBox.getChildren().addAll(yearStatisticsText, datePicker7, yearStatisticsText2,
				israeliLinesCheck4, gazaPowerCheck4, egyptianLinesCheck4, overallDemandCheck4, totalSupplyCheck4,
				temperatureCheck4, yearStatisticsButton, yearStatisticsText3);
		yearStatisticsVBox.setAlignment(Pos.CENTER);

		// All time statistics
		VBox generalStatisticsVBox = new VBox(20);
		Text generalStatisticsText = new Text("All Time Statistics:");
		generalStatisticsText.setFont(Font.font("Gill Sans", FontWeight.BOLD, 20));

		Text generalStatisticsText2 = new Text("Select the Data you Desire:");

		// RadioButtons for the user desired data
		ToggleGroup group4 = new ToggleGroup();
		israeliLinesCheck5 = new RadioButton("Israeli Lines");
		israeliLinesCheck5.setToggleGroup(group2);
		gazaPowerCheck5 = new RadioButton("Gaza Power Plant");
		gazaPowerCheck5.setToggleGroup(group2);
		egyptianLinesCheck5 = new RadioButton("Egyptian Lines");
		egyptianLinesCheck5.setToggleGroup(group2);
		overallDemandCheck5 = new RadioButton("Overall Demand");
		overallDemandCheck5.setToggleGroup(group2);
		powerCutsCheck5 = new RadioButton("Power Hour Cuts");
		powerCutsCheck5.setToggleGroup(group2);
		totalSupplyCheck5 = new RadioButton("Total Supply");
		totalSupplyCheck5.setToggleGroup(group2);
		temperatureCheck5 = new RadioButton("Temperature");
		temperatureCheck5.setToggleGroup(group2);

		generalStatisticsButton = new Button("Search");
		generalStatisticsButton.setScaleX(2);
		generalStatisticsButton.setScaleY(2);

		generalStatisticsText3 = new Text("");
		generalStatisticsText3.setFont(Font.font("Gill Sans", FontWeight.BOLD, 14));
		generalStatisticsVBox.getChildren().addAll(generalStatisticsText, generalStatisticsText2, israeliLinesCheck5,
				gazaPowerCheck5, egyptianLinesCheck5, overallDemandCheck5, totalSupplyCheck5, temperatureCheck5,
				generalStatisticsButton, generalStatisticsText3);
		generalStatisticsVBox.setAlignment(Pos.CENTER);

		statisticsHBox.getChildren().addAll(backButtonVBox, dayStatisticsVBox, monthStatisticsVBox, yearStatisticsVBox,
				generalStatisticsVBox);
		statisticsHBox.setAlignment(Pos.CENTER);
		return statisticsHBox;
	}

	// Getting the data FROM the file and insert it INTO the data structure
	private void readFile(File file) {
		try {
			Scanner scanFile = new Scanner(file);
			while (scanFile.hasNext()) {
				String[] p = scanFile.nextLine().split(",");
				String[] d = p[0].split("-");

				Month month = new Month(d[1]);
				Year year = new Year(d[0]);

				// Getting the data and creating a new Days object
				Date date = new Date(d[2], d[1], d[0]);
				String israeliLines = p[1];
				String gazaPower = p[2];
				String egyptianLines = p[3];
				String totalDailySupply = p[4];
				String overallDemand = p[5];
				String powerCuts = p[6];
				String temp = p[7];

				Day newDay = new Day(date, israeliLines, gazaPower, egyptianLines, overallDemand, powerCuts,
						totalDailySupply, temp);

				// If the year is not available insert it
				if (searchYear(year) == null)
					yearsList.insertSorted(year);

				// If the month is not available insert it
				if (searchMonth(month, year) == null)
					((Year<Integer>) yearsList.search(year).getData()).getMonthList().insertSorted(month);

				// If the day is not available insert it and set the data
				if (searchDay(newDay, month, year) == null)
					((Month) ((Year<Integer>) yearsList.search(year).getData()).getMonthList().search(month).getData())
							.getDayList().insertSorted(newDay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Saving the data structure INTO the file
	private void saveFile(File file) {
		try {
			PrintWriter printFile = new PrintWriter(file);

			// Looping trough the years list
			Node currentYear = yearsList.getHead();
			do {

				// Looping trough the months list
				Node currentMonth = ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData()).getData())
						.getMonthList().getHead();
				do {

					// Looping trough the days list
					Node currentDay = ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData())
							.getData()).getMonthList().search((Month<Integer>) currentMonth.getData()).getData()
							.getDayList().getHead();

					do {

						// Creating a day object with the data and inserting it into the file
						Day day = (Day) currentDay.getData();
						printFile.println(day.getDate().toString() + "," + day.getIsraeliLines() + ","
								+ day.getGazaPowerPlant() + "," + day.getEgyptiansLines() + ","
								+ day.getTotalDailySupply() + "," + day.getOverallDemand() + ","
								+ day.getPowerCutsHoursDay() + "," + day.getTemp());

						currentDay = currentDay.getNext();

					} while (currentDay != ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData())
							.getData()).getMonthList().search((Month<Integer>) currentMonth.getData()).getData()
							.getDayList().getHead());

					currentMonth = currentMonth.getNext();

				} while (currentMonth != ((Year<Integer>) yearsList.search((Year<Integer>) currentYear.getData())
						.getData()).getMonthList().getHead());

				currentYear = currentYear.getNext();

			} while (currentYear != yearsList.getHead());
			// Flushing and closing the file
			printFile.flush();
			printFile.close();
		} catch (FileNotFoundException e) { // If file is not found
			System.out.println("File not found");
		}
	}

	// Checking if the textfields input are valid numbers by parsing them
	private boolean validInput(TextField field1, TextField field2, TextField field3, TextField field4, TextField field5,
			TextField field6, TextField field7) {
		try {
			Double.parseDouble(field1.getText());
			Double.parseDouble(field2.getText());
			Double.parseDouble(field3.getText());
			Double.parseDouble(field4.getText());
			Double.parseDouble(field5.getText());
			Double.parseDouble(field6.getText());
			Double.parseDouble(field7.getText());
			return true;
		} catch (Exception error) { // If there is an error then its not a number
			return false;
		}
	}

	// Checking if a single textfields input is valid numbers by parsing it
	private boolean validInput(TextField field) {
		try {
			Double.parseDouble(field.getText());
			return true;
		} catch (Exception error) { // If there is an error then its not a number
			return false;
		}
	}

	// Searching for a year in the year list
	private Year searchYear(Year year) {
		if (yearsList.search(year) == null)
			return null;
		return (Year) yearsList.search(year).getData();
	}

	// Searching for a month in the month list
	private Month searchMonth(Month month, Year year) {
		if (((Year<Integer>) yearsList.search(year).getData()).getMonthList().search(month) == null)
			return null;
		return (Month) ((Year<Integer>) yearsList.search(year).getData()).getMonthList().search(month).getData();
	}

	// Searching for a day in the day list
	private Day searchDay(Day day, Month month, Year year) {
		if (((Month<Integer>) ((Year<Integer>) yearsList.search(year).getData()).getMonthList().search(month).getData())
				.getDayList().search(day) == null)
			return null;
		return (Day) ((Month<Integer>) ((Year<Integer>) yearsList.search(year).getData()).getMonthList().search(month)
				.getData()).getDayList().search(day).getData();
	}
}