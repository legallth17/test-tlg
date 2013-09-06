require 'test/unit'
require 'bank_record_tagger'

class BankRecordTaggerTest < Test::Unit::TestCase
  def setup
    @tagger = BankRecordTagger.new
  end

  def test_no_tags_are_returned_when_configuration_rules_are_not_set
    assert_equal [],
			@tagger.get_tags("any description")
  end

 
  def test_simple_tags
    @tagger.add_rule("PREL", "debit")
    @tagger.add_rule("EDF","electricity")
    assert_equal ["debit","electricity"],
    	 @tagger.get_tags("xxxx PREL xxx EDF xxxx");
  end
end