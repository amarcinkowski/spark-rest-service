#encoding: utf-8

require 'rest-client'
require 'json'
require "rspec"
require 'spec'
require 'watir-webdriver'

SITE = "localhost"
BROWSER = Watir::Browser.start(SITE, :firefox)
PAGES = {
  "home" => "http://localhost:4568/"
}

Given(/^I navigate to "([^"]*)"$/) do |arg1|
  BROWSER.goto(PAGES[arg1])
end

When(/^I click on element having id "([^"]*)"$/) do |arg1|
  BROWSER.link(:text, arg1).click
end

Then(/^I can see "([^"]*)"$/) do |arg1|
  BROWSER.text.include?(arg1).should == true
end

