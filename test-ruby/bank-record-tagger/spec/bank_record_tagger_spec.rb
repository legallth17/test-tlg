require 'bank_record_tagger'

describe BankRecordTagger do
  describe '#get_tags' do
    it 'returns no tag when tagger has not been configured' do
      tagger = BankRecordTagger.new
      expect(tagger.get_tags("blab abala")).to eq([])
    end

    it 'returns expected tags when tagger has been configured' do
      tagger = BankRecordTagger.new
      tagger.add_rule("PREL", "debit")
      tagger.add_rule("EDF","electricity")

      expect(tagger.get_tags("xxxxx EDF xxxx PRELEVEMENT xxx")).to eq(["debit","electricity"])
    end

    it 'returns no tag when no match' do
      tagger = BankRecordTagger.new
      tagger.add_rule("PREL", "debit")
      tagger.add_rule("EDF","electricity")
      expect(tagger.get_tags("blab abala")).to eq([])
    end

  end
end