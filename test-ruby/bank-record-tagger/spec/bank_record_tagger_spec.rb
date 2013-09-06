require 'bank_record_tagger'

describe BankRecordTagger do
  describe '#get_tags' do
    it 'returns no tag when tagger has not been configured' do
      tagger = BankRecordTagger.new
      expect(tagger.get_tags("blab abala")).to eq(["rr"])
    end
  end
end